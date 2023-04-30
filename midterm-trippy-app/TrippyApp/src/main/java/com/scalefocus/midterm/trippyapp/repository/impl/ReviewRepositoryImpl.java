package com.scalefocus.midterm.trippyapp.repository.impl;

import com.scalefocus.midterm.trippyapp.exception.UserExceptions.UserNotFoundException;
import com.scalefocus.midterm.trippyapp.mapper.ReviewMapper;
import com.scalefocus.midterm.trippyapp.model.Review;
import com.scalefocus.midterm.trippyapp.model.User;
import com.scalefocus.midterm.trippyapp.model.dto.ReviewDto;
import com.scalefocus.midterm.trippyapp.repository.ReviewRepository;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReviewRepositoryImpl implements ReviewRepository {
    private final static String INSERT_REVIEW_SQL_STATEMENT = "INSERT INTO ta.review (username,stamp_created, rating, text_body,business_id) VALUES (?, ?, ? ,?,?)";
    private final static String SELECT_ALL_REVIEWS_SQL_STATEMENT = "SELECT * FROM ta.review";
    private final static String SELECT_REVIEWS_BY_USERNAME_SQL_STATEMENT = "SELECT * FROM ta.review WHERE username=?";
    private final static String SELECT_REVIEWS_BY_ID_SQL_STATEMENT = "SELECT * FROM ta.review WHERE id=?";
    private final static String DELETE_REVIEWS_BY_USERNAME_SQL_STATEMENT = "DELETE FROM ta.review WHERE id=? AND username=?";
    private final UserRepositoryImpl userRepositoryImpl;
    private final HikariDataSource hikariDataSource;
    private final ReviewMapper reviewMapper;

    public ReviewRepositoryImpl(UserRepositoryImpl userRepositoryImpl, HikariDataSource hikariDataSource, ReviewMapper reviewMapper) {
        this.userRepositoryImpl = userRepositoryImpl;
        this.hikariDataSource = hikariDataSource;
        this.reviewMapper = reviewMapper;
    }

    @Override
    public List<Review> getAll() {
        List<Review> reviews = new ArrayList<>();
        try (Connection connection = hikariDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_REVIEWS_SQL_STATEMENT)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                reviews.add(reviewMapper.mapRow(resultSet, resultSet.getRow()));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reviews;
    }

    @Override
    public Long add(Review review) throws SQLException {
        try (Connection connection = hikariDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_REVIEW_SQL_STATEMENT, Statement.RETURN_GENERATED_KEYS)) {
            User user = userRepositoryImpl.getByUsername(review.getUsername());
            if (user == null) {
                throw new UserNotFoundException("username " + review.getUsername());
            }
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setDate(2, Date.valueOf(review.getCreatedOn()));
            preparedStatement.setObject(3, review.getRating().name(), Types.OTHER);
            preparedStatement.setString(4, review.getText());
            preparedStatement.setObject(5, review.getBusiness().getId());
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
    public Review edit(Review review, Long id) throws SQLException {
        return null;
    }

    @Override
    public ReviewDto delete(Review review) {
        try (Connection connection = hikariDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_REVIEWS_BY_USERNAME_SQL_STATEMENT)) {
            preparedStatement.setLong(1, review.getId());
            preparedStatement.setString(2, review.getUsername());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reviewMapper.mapToDto(review);
    }

    @Override
    public Review getById(Long id) {
        Review review = null;
        try (Connection connection = hikariDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_REVIEWS_BY_ID_SQL_STATEMENT)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                review = reviewMapper.mapRow(resultSet, resultSet.getRow());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return review;
    }


    @Override
    public Review getByUsername(String username) {
        return null;
    }

    public List<Review> getReviewsByUsername(String username) {
        List<Review> listOfReviews = new ArrayList<>();
        try (Connection connection = hikariDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_REVIEWS_BY_USERNAME_SQL_STATEMENT)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listOfReviews.add(reviewMapper.mapRow(resultSet, resultSet.getRow()));
            }
            return listOfReviews;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
