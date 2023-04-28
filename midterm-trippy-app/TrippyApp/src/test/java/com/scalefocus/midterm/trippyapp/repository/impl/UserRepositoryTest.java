package com.scalefocus.midterm.trippyapp.repository.impl;

import com.scalefocus.midterm.trippyapp.mapper.UserMapper;
import com.scalefocus.midterm.trippyapp.model.User;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.*;

import static com.scalefocus.midterm.trippyapp.testutils.User.UserConstants.USER_ID;
import static com.scalefocus.midterm.trippyapp.testutils.User.UserFactory.getDefaultUser;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryTest {
    @Mock
    private HikariDataSource hikariDataSource;
    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserRepository userRepository;


    @Test
    public void saveUserToDatabase_success() throws SQLException {

        String sql = "INSERT INTO ta.users (username,email, first_name, last_name, city, joining_date) VALUES (?, ?, ? ,? ,?, ?)";

        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        when(hikariDataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong(1)).thenReturn(1L);
        User user = getDefaultUser();
        Long result = userRepository.add(user);

        Assertions.assertEquals(1L, result);
        verify(preparedStatement, times(1)).setString(1, user.getUsername());
        verify(preparedStatement, times(1)).setString(2, user.getEmail());
        verify(preparedStatement, times(1)).setString(3, user.getFirstName());
        verify(preparedStatement, times(1)).setString(4, user.getLastName());
        verify(preparedStatement, times(1)).setString(5, user.getCity());
        verify(preparedStatement, times(1)).setDate(6, Date.valueOf(user.getJoiningDate()));
        verify(preparedStatement, times(1)).executeUpdate();
        verify(preparedStatement, times(1)).getGeneratedKeys();

    }

    @Test
    public void updateUserInDatabase_success() throws SQLException {

        String sql = "UPDATE ta.users SET username=? , email =?, first_name=?, last_name=?, city= ? WHERE id=? ";
        String sqlId = "SELECT * FROM ta.users WHERE id = ?";

        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        when(hikariDataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(sqlId)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        doNothing().when(preparedStatement).setLong(1, USER_ID);

        when(hikariDataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        User user = getDefaultUser();
        userRepository.update(user, USER_ID);

        verify(preparedStatement, times(1)).setString(1, user.getUsername());
        verify(preparedStatement, times(1)).setString(2, user.getEmail());
        verify(preparedStatement, times(1)).setString(3, user.getFirstName());
        verify(preparedStatement, times(1)).setString(4, user.getLastName());
        verify(preparedStatement, times(1)).setString(5, user.getCity());
        verify(preparedStatement, times(1)).setLong(6, USER_ID);
        verify(preparedStatement, times(1)).executeUpdate();

    }

    @Test
    public void deleteMethod_doNothing_returnFalse() {
        assertFalse(userRepository.delete(getDefaultUser()));
    }
}