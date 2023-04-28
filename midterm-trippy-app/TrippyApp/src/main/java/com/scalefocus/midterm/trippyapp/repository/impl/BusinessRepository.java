package com.scalefocus.midterm.trippyapp.repository.impl;

import com.scalefocus.midterm.trippyapp.mapper.BusinessMapper;
import com.scalefocus.midterm.trippyapp.model.Business;
import com.scalefocus.midterm.trippyapp.model.User;
import com.scalefocus.midterm.trippyapp.repository.CustomRepository;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BusinessRepository implements CustomRepository<Business> {
    private final static String INSERT_BUSINESS_SQL_STATEMENT = """
            INSERT INTO ta.businesses (name, city, business_type, address, email, phone, website) 
            VALUES (?,?,?,?,?,?,?)""";
    private final static String SELECT_ALL_BUSINESSES_STATEMENT = "SELECT * FROM ta.businesses";

    private final static String GET_AVG_RATING_SQL = """
             SELECT AVG(CASE r.rating
                           WHEN 'VERY_POOR' THEN 1.00
                           WHEN 'POOR' THEN 2.00
                           WHEN 'AVERAGE' THEN 3.00
                           WHEN 'GOOD' THEN 4.00
                           WHEN 'VERY_GOOD' THEN 5.00
                         END) AS average_rating
             FROM ta.review AS r
             JOIN ta.business_reviews AS br ON br.review_id = r.id
             JOIN ta.business AS b ON b.id = br.business_id
             WHERE b.id = (?);
            """;

    private final HikariDataSource hikariDataSource;
    private final BusinessMapper businessMapper;

    public BusinessRepository(HikariDataSource hikariDataSource, BusinessMapper businessMapper) {
        this.hikariDataSource = hikariDataSource;
        this.businessMapper = businessMapper;
    }


    @Override
    public List<Business> getAll() {
        List<Business> businesses = new ArrayList<>();
        try (PreparedStatement preparedStatement = hikariDataSource.getConnection().prepareStatement(SELECT_ALL_BUSINESSES_STATEMENT)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                businesses.add(businessMapper.mapRow(resultSet, resultSet.getRow()));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return businesses;
    }

    @Override
    public Long add(Business business) throws SQLException {
        try (PreparedStatement preparedStatement = hikariDataSource.getConnection().prepareStatement(INSERT_BUSINESS_SQL_STATEMENT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, business.getName());
            preparedStatement.setString(2, business.getCity());
            preparedStatement.setObject(3, business.getBusinessType().name(), Types.OTHER);
            preparedStatement.setString(4, business.getAddress());
            preparedStatement.setString(5, business.getEmail());
            preparedStatement.setString(6, business.getPhone());
            preparedStatement.setString(7, business.getWebsite());
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
    public User update(Business business, Long id) throws SQLException {
        return null;
    }

    @Override
    public Boolean delete(Business business) {
        return null;
    }

    @Override
    public Business getById(Long id) {
        return null;
    }

    @Override
    public Business getByEmail(String email) {
        return null;
    }

    @Override
    public Business getByUsername(String username) {
        return null;
    }

}
