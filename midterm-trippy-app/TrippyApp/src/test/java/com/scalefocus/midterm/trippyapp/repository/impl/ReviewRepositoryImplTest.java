package com.scalefocus.midterm.trippyapp.repository.impl;

import com.scalefocus.midterm.trippyapp.mapper.ReviewMapper;
import com.scalefocus.midterm.trippyapp.model.Review;
import com.scalefocus.midterm.trippyapp.model.User;
import com.scalefocus.midterm.trippyapp.repository.UserRepository;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.*;

import static com.scalefocus.midterm.trippyapp.testutils.Review.ReviewConstants.REVIEW_ID;
import static com.scalefocus.midterm.trippyapp.testutils.Review.ReviewConstants.REVIEW_USERNAME;
import static com.scalefocus.midterm.trippyapp.testutils.Review.ReviewFactory.getDefaultReview;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReviewRepositoryImplTest {
    @Mock
    private HikariDataSource hikariDataSource;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ReviewMapper reviewMapper;

    @InjectMocks
    private ReviewRepositoryImpl reviewRepository;

    @Test
    public void createReview_noException_success() throws Exception {
        String sql = "INSERT INTO ta.review (username,stamp_created, rating, text_body,business_id) VALUES (?, ?, ? ,?,?)";

        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        when(hikariDataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong(1)).thenReturn(1L);

        Review review = getDefaultReview();
        User user = new User();
        user.setUsername(review.getUsername());
        when(userRepository.getByUsername(any())).thenReturn(user);
        Long result = reviewRepository.add(review);

        Assertions.assertEquals(1L, result);
        verify(preparedStatement, times(1)).setString(1, review.getUsername());
        verify(preparedStatement, times(1)).setDate(2, Date.valueOf(review.getCreatedOn()));
        verify(preparedStatement, times(1)).setObject(3, review.getRating().name(), Types.OTHER);
        verify(preparedStatement, times(1)).setString(4, review.getText());
        verify(preparedStatement, times(1)).setObject(5, review.getBusiness().getId());
        verify(preparedStatement, times(1)).executeUpdate();
        verify(preparedStatement, times(1)).getGeneratedKeys();

    }

    @Test
    public void updateReview_noException_success() throws Exception {
        String sql = "UPDATE ta.review SET username=?, stamp_created= ?, rating=?, text_body=?, business_id=? WHERE id = ? AND username = ?";
        String sqlId = "SELECT * FROM ta.review WHERE id=?";


        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        when(hikariDataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(sqlId)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        doNothing().when(preparedStatement).setLong(1, REVIEW_ID);

        when(hikariDataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        Review review = getDefaultReview();
        review.setUsername(REVIEW_USERNAME);
        reviewRepository.edit(review, REVIEW_ID);


        verify(preparedStatement, times(1)).setString(1, review.getUsername());
        verify(preparedStatement, times(1)).setDate(2, Date.valueOf(review.getCreatedOn()));
        verify(preparedStatement, times(1)).setObject(3, review.getRating().name(), Types.OTHER);
        verify(preparedStatement, times(1)).setString(4, review.getText());
        verify(preparedStatement, times(1)).setObject(5, review.getBusiness().getId());
        verify(preparedStatement, times(1)).setLong(6, REVIEW_ID);
        verify(preparedStatement, times(1)).setString(7, review.getUsername());

    }

    @Test
    public void deleteReview_noException_success() throws Exception {
        String sql = "DELETE FROM ta.review WHERE id=? AND username=?";

        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);

        when(hikariDataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        Review review = getDefaultReview();
        reviewRepository.delete(review);

    }

}