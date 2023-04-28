package com.scalefocus.midterm.trippyapp.repository.impl;

import com.scalefocus.midterm.trippyapp.constants.enums.BusinessType;
import com.scalefocus.midterm.trippyapp.mapper.BusinessMapper;
import com.scalefocus.midterm.trippyapp.model.Business;
import com.scalefocus.midterm.trippyapp.repository.BusinessRepository;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BusinessRepositoryImpl implements BusinessRepository {
    private final static String INSERT_BUSINESS_SQL_STATEMENT = """
            INSERT INTO ta.businesses (name, city, business_type, address, email, phone, website)
            VALUES (?,?,?,?,?,?,?)""";
    private final static String SELECT_ALL_BUSINESSES_STATEMENT = "SELECT * FROM ta.businesses";
    private final static String SELECT_BUSINESSES_BY_CITY_STATEMENT = "SELECT * FROM ta.businesses WHERE city = ?";
    private final static String SELECT_BUSINESSES_BY_TYPE_STATEMENT = "SELECT * FROM ta.businesses WHERE business_type = ?";
    private final static String SELECT_BUSINESSES_BY_ID_STATEMENT = "SELECT * FROM ta.businesses WHERE id = ?";
    private final static String SELECT_BUSINESSES_BY_EMAIL_STATEMENT = "SELECT * FROM ta.businesses WHERE email = ?";
    private final static String SELECT_BUSINESSES_BY_NAME_STATEMENT = "SELECT * FROM ta.businesses WHERE name = ?";
    private final static String SELECT_BUSINESSES_BY_NAME_AND_CITY_STATEMENT = "SELECT * FROM ta.businesses WHERE name = ? and city=?";
    private final static String UPDATE_BUSINESSES_BY_ID_STATEMENT = "UPDATE ta.businesses SET name=?, city=?, business_type=?, address=?, email=?, phone=?, website=?  WHERE id = ?";

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

    public BusinessRepositoryImpl(HikariDataSource hikariDataSource, BusinessMapper businessMapper) {
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
    public Business update(Business business, Long id) throws SQLException {
        Business oldBusiness = getById(id);
        try (PreparedStatement preparedStatement = hikariDataSource.getConnection().prepareStatement(UPDATE_BUSINESSES_BY_ID_STATEMENT)) {
            preparedStatement.setString(1, business.getName());
            preparedStatement.setString(2, business.getCity());
            preparedStatement.setObject(3, business.getBusinessType().name(), Types.OTHER);
            preparedStatement.setString(4, business.getAddress());
            preparedStatement.setString(5, business.getEmail());
            preparedStatement.setString(6, business.getPhone());
            preparedStatement.setString(7, business.getWebsite());
            preparedStatement.setLong(8, id);
            preparedStatement.executeUpdate();

            return oldBusiness;
        }
    }

    @Override
    public Boolean delete(Business business) {
        return null;
    }

    @Override
    public Business getById(Long id) {
        try (PreparedStatement preparedStatement = hikariDataSource.getConnection().prepareStatement(SELECT_BUSINESSES_BY_ID_STATEMENT)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Business business = null;
            if (resultSet.next()) {
                business = businessMapper.mapRow(resultSet, resultSet.getRow());
            }
            return business;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Business getByEmail(String email) {
        try (PreparedStatement preparedStatement = hikariDataSource.getConnection().prepareStatement(SELECT_BUSINESSES_BY_EMAIL_STATEMENT)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            Business business = null;
            if (resultSet.next()) {
                business = businessMapper.mapRow(resultSet, resultSet.getRow());
            }
            return business;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Business getByUsername(String username) {
        return null;
    }


    @Override
    public List<Business> getBusinessByCityName(String city) {
        List<Business> businesses = new ArrayList<>();
        try (PreparedStatement preparedStatement = hikariDataSource.getConnection().prepareStatement(SELECT_BUSINESSES_BY_CITY_STATEMENT)) {
            preparedStatement.setString(1, city);
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
    public List<Business> getBusinessByType(BusinessType type) {
        List<Business> businesses = new ArrayList<>();
        try (PreparedStatement preparedStatement = hikariDataSource.getConnection().prepareStatement(SELECT_BUSINESSES_BY_TYPE_STATEMENT)) {
            preparedStatement.setObject(1, type.name(), Types.OTHER);
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
    public Business getByName(String name) {
        try (PreparedStatement preparedStatement = hikariDataSource.getConnection().prepareStatement(SELECT_BUSINESSES_BY_NAME_STATEMENT)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            Business business = null;
            if (resultSet.next()) {
                business = businessMapper.mapRow(resultSet, resultSet.getRow());
            }
            return business;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Business getByNameAndCity(String name, String city) {
        try (PreparedStatement preparedStatement = hikariDataSource.getConnection().prepareStatement(SELECT_BUSINESSES_BY_NAME_AND_CITY_STATEMENT)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, city);
            ResultSet resultSet = preparedStatement.executeQuery();
            Business business = null;
            if (resultSet.next()) {
                business = businessMapper.mapRow(resultSet, resultSet.getRow());
            }
            return business;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
