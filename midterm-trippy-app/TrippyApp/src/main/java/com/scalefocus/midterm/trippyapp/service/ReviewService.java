package com.scalefocus.midterm.trippyapp.service;

import com.scalefocus.midterm.trippyapp.controller.request.ReviewRequest;
import com.scalefocus.midterm.trippyapp.model.Review;
import com.scalefocus.midterm.trippyapp.model.dto.ReviewDto;

import java.util.List;

public interface ReviewService {
    Long createReview(ReviewRequest reviewRequest, String username);

    ReviewDto editReview(ReviewRequest reviewRequest, Integer id, String username);

    ReviewDto deleteReview(Integer id, String username);

    ReviewDto getReviewById(Long id);

    List<ReviewDto> getAllReviews();

    List<Review> getReviewsByUsername(String username);
}
