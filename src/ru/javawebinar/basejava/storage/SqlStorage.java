package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

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
            try (PreparedStatement ps = connection.prepareStatement("DELETE FROM contact WHERE resume_uuid=?")) {
                ps.setString(1, uuid);
                ps.execute();
            }
            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO contact (resume_uuid, type, value)  VALUES (?,?,?)")) {
                for (Map.Entry<ContactType, String> contacts : resume.getContacts().entrySet()) {
                    ps.setString(1, uuid);
                    ps.setString(2, contacts.getKey().name());
                    ps.setString(3, contacts.getValue());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
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
            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
                for (Map.Entry<ContactType, String> contacts : resume.getContacts().entrySet()) {
                    ps.setString(1, resume.getUuid());
                    ps.setString(2, contacts.getKey().name());
                    ps.setString(3, contacts.getValue());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.executeSqlQuery("" +
                        "    SELECT * FROM resume r " +
                        " LEFT JOIN contact c " +
                        "        ON r.uuid = c.resume_uuid " +
                        "     WHERE r.uuid =?",
                (ps) -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(uuid, rs.getString("full_name"));
                    do {
                        String value = rs.getString("value");
                        if (value == null) {
                            break;
                        }
                        ContactType type = ContactType.valueOf(rs.getString("type"));
                        resume.addContact(type, value);
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
        Map<String, Resume> resumes = new HashMap<>();
        sqlHelper.executeSqlQuery("SELECT * FROM resume ORDER BY full_name, uuid", (ps) -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String uuid = rs.getString("uuid");
                resumes.put(uuid, new Resume(uuid, rs.getString("full_name")));
            }
            return null;
        });
        sqlHelper.executeSqlQuery("SELECT * FROM contact ORDER BY resume_uuid", (ps) -> {
            ResultSet rs = ps.executeQuery();
            while ((rs.next())) {
                resumes.get(rs.getString("resume_uuid")).addContact(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
            }
            return null;
        });
        List<Resume> result = new ArrayList<>(resumes.values());
        Collections.sort(result);
        return result;
    }

    @Override
    public int size() {
        return sqlHelper.executeSqlQuery("SELECT COUNT(*) FROM resume", (ps) -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        });

    }
}
