package com.example.homework6.repository.impl.sqlite.sqliteAbstract;

import java.sql.*;
import java.util.Optional;

public abstract class SqliteAbstractRepository {

    protected Connection newConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return DriverManager.getConnection("jdbc:sqlite:/home/luka/Documents/SQLITEdatabases/homework5.db");
    }

    protected void closeStatement(Statement statement) {
        try {
            Optional.of(statement).get().close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    protected void closeResultSet(ResultSet resultSet) {
        try {
            Optional.of(resultSet).get().close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    protected void closeConnection(Connection connection) {
        try {
            Optional.of(connection).get().close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
