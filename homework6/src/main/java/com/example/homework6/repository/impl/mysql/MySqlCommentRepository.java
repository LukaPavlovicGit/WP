package com.example.homework6.repository.impl.mysql;

import com.example.homework6.entities.Comment;
import com.example.homework6.repository.CommentRepository;
import com.example.homework6.repository.impl.mysql.mysqlAbstract.MySqlAbstractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlCommentRepository extends MySqlAbstractRepository implements CommentRepository {

    @Override
    public Comment insert(Comment comment) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("INSERT INTO comments (post_id, author, content) VALUES(?, ?, ?)", generatedColumns);
            preparedStatement.setInt(1, comment.getPostId());
            preparedStatement.setString(2, comment.getAuthor());
            preparedStatement.setString(3, comment.getContent());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                comment.setId(resultSet.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return comment;
    }

    @Override
    public List<Comment> getAll() {
        List<Comment> comments = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM comments";
        try{
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                comments.add(new Comment(
                        resultSet.getInt("id"),
                        resultSet.getInt("post_id"),
                        resultSet.getString("author"),
                        resultSet.getString("content")
                ));
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return comments;
    }

    @Override
    public List<Comment> getAllByPostId(int postId) {
        List<Comment> comments = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM comments WHERE post_id = ?";
        try{
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, postId);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                comments.add(new Comment(
                        resultSet.getInt("id"),
                        resultSet.getInt("post_id"),
                        resultSet.getString("author"),
                        resultSet.getString("content")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return comments;
    }

    @Override
    public Comment findById(int id) {
        Comment comment = new Comment();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM comments WHERE id = ?";
        try{
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                comment.setId(resultSet.getInt("id"));
                comment.setPostId(resultSet.getInt("post_id"));
                comment.setAuthor(resultSet.getString("author"));
                comment.setContent(resultSet.getString("content"));
            }

        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return comment;
    }

    @Override
    public void delete(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String query = "DELETE FROM comments WHERE id = ?";
        try{
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }
}
