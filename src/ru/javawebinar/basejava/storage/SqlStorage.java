package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.ConnectionFactory;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        SqlHelper.executeSqlQuery(connectionFactory, "DELETE FROM resume",
                (connection, ps) -> {
                    ps.execute();
                    return null;
                });
    }

    @Override
    public void update(Resume resume) {
        SqlHelper.executeSqlQuery(connectionFactory, "UPDATE resume SET full_name =? WHERE uuid=?",
                (connection, ps) -> {
                    ps.setString(1, resume.getFullName());
                    ps.setString(2, resume.getUuid());
                    ps.execute();
                    return null;
                });
    }

    @Override
    public void save(Resume resume) {
        SqlHelper.executeSqlQuery(connectionFactory, "INSERT INTO resume (uuid, full_name) VALUES (?,?)",
                (connection, ps) -> {
                    ps.setString(1, resume.getUuid());
                    ps.setString(2, resume.getFullName());
                    ps.execute();
                    return null;
                });
    }

    @Override
    public Resume get(String uuid) {
        return (Resume) SqlHelper.executeSqlQuery(connectionFactory, "SELECT * FROM resume WHERE uuid =?",
                (connection, ps) -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    return new Resume(uuid, rs.getString("full_name"));
                });
    }

    @Override
    public void delete(String uuid) {
        SqlHelper.executeSqlQuery(connectionFactory, "DELETE FROM resume WHERE uuid =?",
                (connection, ps) -> {
                    ps.setString(1, uuid);
                    ps.execute();
                    return null;
                });
    }

    @Override
    public List<Resume> getAllSorted() {
        return (List<Resume>) SqlHelper.executeSqlQuery(connectionFactory, "SELECT * FROM resume ORDER BY full_name",
                (connection, ps) -> {
                    List<Resume> result = new ArrayList<>();
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        result.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name")));
                    }
                    return result;
                });
    }

    @Override
    public int size() {
        /*try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM resume")) {
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return 0;
            }
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new StorageException(e);
        }*/
        return (int) SqlHelper.executeSqlQuery(connectionFactory, "SELECT COUNT(*) FROM resume", (connection, ps) -> {
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return 0;
            }
            return rs.getInt(1);
        });

    }
}
