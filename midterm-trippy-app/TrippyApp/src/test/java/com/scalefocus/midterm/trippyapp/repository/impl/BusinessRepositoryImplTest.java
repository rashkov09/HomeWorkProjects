package com.scalefocus.midterm.trippyapp.repository.impl;

import com.scalefocus.midterm.trippyapp.mapper.BusinessMapper;
import com.scalefocus.midterm.trippyapp.model.Business;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.*;

import static com.scalefocus.midterm.trippyapp.testutils.Business.BusinessConstants.BUSINESS_ID;
import static com.scalefocus.midterm.trippyapp.testutils.Business.BusinessFactory.getDefaultBusiness;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)

public class BusinessRepositoryImplTest {
    @Mock
    private HikariDataSource hikariDataSource;
    @Mock
    private BusinessMapper businessMapper;

    @InjectMocks
    private BusinessRepositoryImpl businessRepository;

    @Test
    public void saveBusinessToDatabase_success() throws SQLException {

        String sql = """
                INSERT INTO ta.businesses (name, city, business_type, address, email, phone, website)
                VALUES (?,?,?,?,?,?,?)""";

        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        when(hikariDataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong(1)).thenReturn(1L);
        Business business = getDefaultBusiness();
        Long result = businessRepository.add(business);

        Assertions.assertEquals(1L, result);
        verify(preparedStatement, times(1)).setString(1, business.getName());
        verify(preparedStatement, times(1)).setString(2, business.getCity());
        verify(preparedStatement, times(1)).setObject(3, business.getBusinessType().name(), Types.OTHER);
        verify(preparedStatement, times(1)).setString(4, business.getAddress());
        verify(preparedStatement, times(1)).setString(5, business.getEmail());
        verify(preparedStatement, times(1)).setString(6, business.getPhone());
        verify(preparedStatement, times(1)).setString(7, business.getWebsite());

        verify(preparedStatement, times(1)).executeUpdate();
        verify(preparedStatement, times(1)).getGeneratedKeys();

    }

    @Test
    public void updateBusinessInDatabase_success() throws SQLException {

        String sql = "UPDATE ta.businesses SET name=?, city=?, business_type=?, address=?, email=?, phone=?, website=?  WHERE id = ?";
        String sqlId = "SELECT * FROM ta.businesses WHERE id = ?";
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        when(hikariDataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(sqlId)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        doNothing().when(preparedStatement).setLong(1, BUSINESS_ID);

        when(hikariDataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        Business business = getDefaultBusiness();
        businessRepository.update(business, BUSINESS_ID);

        verify(preparedStatement, times(1)).setString(1, business.getName());
        verify(preparedStatement, times(1)).setString(2, business.getCity());
        verify(preparedStatement, times(1)).setObject(3, business.getBusinessType().name(), Types.OTHER);
        verify(preparedStatement, times(1)).setString(4, business.getAddress());
        verify(preparedStatement, times(1)).setString(5, business.getEmail());
        verify(preparedStatement, times(1)).setString(6, business.getPhone());
        verify(preparedStatement, times(1)).setString(7, business.getWebsite());

        verify(preparedStatement, times(1)).executeUpdate();

    }
}