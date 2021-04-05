package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        sqlHelper.executeSqlQuery("DELETE FROM resume", (ps) -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        String uuid = resume.getUuid();
        sqlHelper.transactionalExecuteSqlQuery(connection -> {
            try (PreparedStatement ps = connection.prepareStatement("UPDATE resume SET full_name =? WHERE uuid=?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, uuid);
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(uuid);
                }
            }
            deleteResumeData(resume, connection, "contact");
            insertContacts(resume, connection);
            deleteResumeData(resume, connection, "section");
            insertSections(resume, connection);
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecuteSqlQuery(connection -> {
            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            }
            insertContacts(resume, connection);
            insertSections(resume, connection);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
       return sqlHelper.executeSqlQuery("" +
                        "    SELECT * FROM resume r " +
                        " LEFT JOIN contact c " +
                        "        ON r.uuid = c.resume_uuid " +
                        " LEFT JOIN section s " +
                        "        ON r.uuid = s.resume_uuid " +
                        "     WHERE r.uuid =?",
                (ps) -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(uuid, rs.getString("full_name"));
                    do {
                        addContact(resume, rs);
                        addSection(resume, rs);
                    } while (rs.next());
                    return resume;
                });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.executeSqlQuery("DELETE FROM resume WHERE uuid =?", (ps) -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        Map<String, Resume> resumes = new LinkedHashMap<>();
        sqlHelper.transactionalExecuteSqlQuery(connection -> {
            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    resumes.put(uuid, new Resume(uuid, rs.getString("full_name")));
                }
            }
            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM contact ORDER BY resume_uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next())
                    addContact(resumes.get(rs.getString("resume_uuid")), rs);
            }
            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM section ORDER BY resume_uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next())
                    addSection(resumes.get(rs.getString("resume_uuid")), rs);
            }
            return null;
        });
        return new ArrayList<>(resumes.values());
    }

    @Override
    public int size() {
        return sqlHelper.executeSqlQuery("SELECT COUNT(*) FROM resume", (ps) -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        });
    }

    private void deleteResumeData(Resume resume, Connection connection, String tableName) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM " + tableName + " WHERE resume_uuid=?")) {
            ps.setString(1, resume.getUuid());
            ps.execute();
        }
    }

    private void insertContacts(Resume resume, Connection connection) throws SQLException {
        if (resume.getContacts().size() > 0) {
            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
                for (Map.Entry<ContactType, String> contacts : resume.getContacts().entrySet()) {
                    ps.setString(1, resume.getUuid());
                    ps.setString(2, contacts.getKey().name());
                    ps.setString(3, contacts.getValue());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
        }
    }

    private void insertSections(Resume resume, Connection connection) throws SQLException {
        if (resume.getSections().size() > 0) {
            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO section (resume_uuid, sect_type, sect_value) VALUES (?,?,?)")) {
                for (Map.Entry<SectionType, AbstractSection> sections : resume.getSections().entrySet()) {
                    ps.setString(1, resume.getUuid());
                    SectionType sectionType = sections.getKey();
                    ps.setString(2, sectionType.name());
                    switch (sectionType) {
                        case OBJECTIVE, PERSONAL -> {
                            ps.setString(3, sections.getValue().toString());
                            ps.addBatch();
                        }
                        case ACHIEVEMENT, QUALIFICATIONS -> {
                            String value = String.join("/n", ((ListSection) sections.getValue()).getItems());
                            ps.setString(3, value);
                            ps.addBatch();
                        }
                    }
                }
                ps.executeBatch();
            }
        }
    }

    private void addContact(Resume resume, ResultSet rs) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            String type = rs.getString("type");
            resume.addContact(ContactType.valueOf(type), value);
        }
    }

    private void addSection(Resume resume, ResultSet rs) throws SQLException {
        String value = rs.getString("sect_value");
        if (value != null) {
            SectionType sectionType = SectionType.valueOf(rs.getString("sect_type"));
            switch (sectionType) {
                case OBJECTIVE, PERSONAL -> resume.addSection(sectionType, new TextSection(value));
                case ACHIEVEMENT, QUALIFICATIONS -> resume.addSection(sectionType, new ListSection(value.split("/n")));
            }
        }
    }
}
