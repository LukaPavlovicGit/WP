package com.example.homework6.repository.impl.mysql;

import com.example.homework6.entities.User;
import com.example.homework6.repository.UserRepository;
import com.example.homework6.repository.impl.mysql.mysqlAbstract.MySqlAbstractRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlUserRepository extends MySqlAbstractRepository implements UserRepository {

    @Override
    public User findUser(String username) {
        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername( resultSet.getString("username"));
                user.setFirstname(resultSet.getString("firstname"));
                user.setLastname(resultSet.getString("lastname"));
                user.setRole(resultSet.getString("role"));
                user.setHashedPassword(resultSet.getString("password"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return user;
    }

    @Override
    public Boolean insert(User user) {

        if(findUser(user.getUsername()) != null){
            return false;
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();
            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("INSERT INTO users (username, firstname, lastname, role, password) VALUES(?, ?, ?, ?, ?)", generatedColumns);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getFirstname());
            preparedStatement.setString(3, user.getLastname());
            preparedStatement.setString(4, user.getRole());
            preparedStatement.setString(5, user.getHashedPassword());
            preparedStatement.executeUpdate();
            preparedStatement.getGeneratedKeys();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }

        return true;
    }
}
