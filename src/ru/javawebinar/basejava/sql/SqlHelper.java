package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper{
    public static Object executeSqlQuery(ConnectionFactory connectionFactory, String sqlStatement, QueryProcess queryProcess) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sqlStatement)) {
            return queryProcess.execute(connection, ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public static interface QueryProcess <T>{
        T execute(Connection connection, PreparedStatement ps) throws SQLException;
    }
}
