package com.scalefocus.midterm.trippyapp.repository.impl;

import com.scalefocus.midterm.trippyapp.mapper.UserMapper;
import com.scalefocus.midterm.trippyapp.model.User;
import com.scalefocus.midterm.trippyapp.repository.Repository;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.*;

@org.springframework.stereotype.Repository
public class RepositoryImpl implements Repository<User> {
    private final static String INSERT_SQL_STATEMENT = "INSERT INTO ta.user (username, first_name, last_name, city, joining_date) VALUES (?, ? ,? ,?, ?)";
    private final static String UPDATE_SQL_STATEMENT = "UPDATE ta.user SET username=? , first_name=?, last_name=?, city= ? WHERE id=? ";
    private final static String SELECT_BY_ID_SQL_STATEMENT = "SELECT * FROM ta.user WHERE id = ?";

    private final HikariDataSource hikariDataSource;
    private final UserMapper userMapper;

    @Autowired
    public RepositoryImpl(HikariDataSource hikariDataSource, UserMapper userMapper) {
        this.hikariDataSource = hikariDataSource;
        this.userMapper = userMapper;
    }

    @Override
    public Long add(User user) throws SQLException {
        try (PreparedStatement preparedStatement = hikariDataSource.getConnection().prepareStatement(INSERT_SQL_STATEMENT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getCity());
            preparedStatement.setDate(5, Date.valueOf(user.getJoiningDate()));
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();

            if (rs.next()) {
                return rs.getLong(1);
            }
            rs.close();
            return 0L;
        }
    }

    @Override
    public User update(User user, Long id) {
        User oldUser = getById(id);
        try (PreparedStatement preparedStatement = hikariDataSource.getConnection().prepareStatement(UPDATE_SQL_STATEMENT)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getCity());
            preparedStatement.setLong(5,user.getId());
            int result =   preparedStatement.executeUpdate();

            return oldUser;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public Boolean delete(User object) {
        return null;
    }

    @Override
    public User getById(Long id) {
        try (PreparedStatement preparedStatement = hikariDataSource.getConnection().prepareStatement(SELECT_BY_ID_SQL_STATEMENT)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return userMapper.mapRow(resultSet, resultSet.getRow());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
      return null;
    }
}
