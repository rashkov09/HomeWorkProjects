package com.scalefocus.midterm.trippyapp.mapper;

import com.scalefocus.midterm.trippyapp.constants.enums.ReviewRating;
import com.scalefocus.midterm.trippyapp.controller.request.ReviewRequest;
import com.scalefocus.midterm.trippyapp.model.Business;
import com.scalefocus.midterm.trippyapp.model.Review;
import com.scalefocus.midterm.trippyapp.model.dto.ReviewDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewMapper implements RowMapper<Review> {

    public ReviewDto mapToDto(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setRating(review.getRating());
        reviewDto.setUsername(review.getUsername());
        reviewDto.setCreatedOn(review.getCreatedOn());
        reviewDto.setText(review.getText());
        reviewDto.setBusiness(review.getBusiness());

        return reviewDto;
    }


    public Review mapFromRequest(ReviewRequest reviewRequest) {
        Review review = new Review();
        review.setRating(ReviewRating.values()[Integer.parseInt(reviewRequest.getRating())-1]);
        review.setText(reviewRequest.getText());

        return review;
    }

    @Override
    public Review mapRow(ResultSet rs, int rowNum) throws SQLException {
        Review review = new Review();
        review.setId(rs.getLong("id"));
        review.setUsername(rs.getString("username"));
        review.setRating(ReviewRating.valueOf(rs.getString("rating")));
        review.setText(rs.getString("text_body"));
        Business business = new Business();
        business.setId(rs.getLong("business_id"));
        review.setBusiness(business);

        return review;
    }
}
