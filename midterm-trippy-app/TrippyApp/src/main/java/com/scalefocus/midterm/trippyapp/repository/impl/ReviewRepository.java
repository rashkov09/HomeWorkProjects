package com.scalefocus.midterm.trippyapp.repository.impl;

import com.scalefocus.midterm.trippyapp.constants.enums.ReviewRating;
import com.scalefocus.midterm.trippyapp.exception.UserExceptions.UserNotFoundException;
import com.scalefocus.midterm.trippyapp.mapper.ReviewMapper;
import com.scalefocus.midterm.trippyapp.model.Review;
import com.scalefocus.midterm.trippyapp.model.User;
import com.scalefocus.midterm.trippyapp.repository.BusinessRepository;
import com.scalefocus.midterm.trippyapp.repository.CustomRepository;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReviewRepository implements CustomRepository<Review> {
    private final static String INSERT_REVIEW_SQL_STATEMENT = "INSERT INTO ta.review (username,stamp_created, rating, text_body,business_id) VALUES (?, ?, ? ,?,?)";
    private final static String SELECT_ALL_REVIEWS_SQL_STATEMENT = "SELECT * FROM ta.review";
    private final static String SELECT_REVIEWS_BY_USERNAME_SQL_STATEMENT = "SELECT * FROM ta.review WHERE username=?";
    private final UserRepository userRepository;
    private final HikariDataSource hikariDataSource;
    private final ReviewMapper reviewMapper;

    public ReviewRepository(UserRepository userRepository, HikariDataSource hikariDataSource, ReviewMapper reviewMapper) {
        this.userRepository = userRepository;
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
            User user = userRepository.getByUsername(review.getUsername());
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
    public Boolean delete(Review review) {
        return null;
    }

    @Override
    public Review getById(Long id) {
        return null;
    }

    @Override
    public Review getByEmail(String email) {
        return null;
    }

    @Override
    public Review getByUsername(String username) {
        return null;
    }

    public List<Review> getReviewsByUsername(String username) {
        List<Review> listOfReviews = new ArrayList<>();
        try (Connection connection = hikariDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_REVIEWS_BY_USERNAME_SQL_STATEMENT)) {
            preparedStatement.setString(1,username);
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
