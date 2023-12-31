package com.scalefocus.midterm.trippyapp.repository.impl;

import com.scalefocus.midterm.trippyapp.mapper.UserMapper;
import com.scalefocus.midterm.trippyapp.model.User;
import com.scalefocus.midterm.trippyapp.repository.UserRepository;
import com.zaxxer.hikari.HikariDataSource;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final static String INSERT_USER_SQL_STATEMENT = "INSERT INTO ta.users (username,email, first_name, last_name, city, joining_date) VALUES (?, ?, ? ,? ,?, ?)";
    private final static String UPDATE_USER_SQL_STATEMENT = "UPDATE ta.users SET username=? , email =?, first_name=?, last_name=?, city= ? WHERE id=? ";
    private final static String FIND_USER_BY_ID_SQL_STATEMENT = "SELECT * FROM ta.users WHERE id = ?";
    private final static String FIND_USER_BY_USERNAME_SQL_STATEMENT = "SELECT * FROM ta.users WHERE username = ?";
    private final static String FIND_USER_BY_EMAIL_SQL_STATEMENT = "SELECT * FROM ta.users WHERE email = ?";
    private final static String SELECT_ALL_USERS_STATEMENT = "SELECT * FROM ta.users";

    private final HikariDataSource hikariDataSource;
    private final UserMapper userMapper;

    @Autowired
    public UserRepositoryImpl(HikariDataSource hikariDataSource, UserMapper userMapper) {
        this.hikariDataSource = hikariDataSource;
        this.userMapper = userMapper;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = hikariDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS_STATEMENT)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                users.add(userMapper.mapRow(resultSet, resultSet.getRow()));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public Long add(User user) throws SQLException {
        try (Connection connection = hikariDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL_STATEMENT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());
            preparedStatement.setString(5, user.getCity());
            preparedStatement.setDate(6, Date.valueOf(user.getJoiningDate()));
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
    public User edit(User user, Long id) throws SQLException {
        User oldUser = getById(id);
        try (Connection connection = hikariDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_SQL_STATEMENT)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());
            preparedStatement.setString(5, user.getCity());
            preparedStatement.setLong(6, id);
            preparedStatement.executeUpdate();

            return oldUser;
        }

    }

    @Override
    public User getById(Long id) {
        try (Connection connection = hikariDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_ID_SQL_STATEMENT)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = userMapper.mapRow(resultSet, resultSet.getRow());
            }
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getByEmail(String email) {
        return getUser(email, FIND_USER_BY_EMAIL_SQL_STATEMENT);
    }

    @Override
    public User getByUsername(String username) {
        return getUser(username, FIND_USER_BY_USERNAME_SQL_STATEMENT);
    }

    @Nullable
    private User getUser(String param, String sql) {
        User user = null;
        try (Connection connection = hikariDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, param);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = userMapper.mapRow(resultSet, resultSet.getRow());
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }


}
