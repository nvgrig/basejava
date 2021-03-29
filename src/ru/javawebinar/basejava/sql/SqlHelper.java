package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class SqlHelper {
    public final ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public <T> T executeSqlQuery(String sqlStatement, QueryProcess<T> queryProcess) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sqlStatement)) {
            return queryProcess.execute(ps);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new ExistStorageException("");
            }
            throw new StorageException(e);
        }
    }

    public <T> T transactionalExecuteSqlQuery(TransactionProcess<T> transactionProcess) {
        try (Connection connection = connectionFactory.getConnection()) {
            try {
                connection.setAutoCommit(false);
                T result = transactionProcess.execute(connection);
                connection.commit();
                return result;
            } catch (SQLException e) {
                connection.rollback();
                if (e.getSQLState().equals("23505")) {
                    throw new ExistStorageException("");
                }
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
        return null;
    }

    public interface QueryProcess<T> {
        T execute(PreparedStatement ps) throws SQLException;
    }

    public interface TransactionProcess<T> {
        T execute(Connection connection) throws SQLException;
    }

    public void insertContact(Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO contact (resume_uuid, type, value)  VALUES (?,?,?)")) {
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
