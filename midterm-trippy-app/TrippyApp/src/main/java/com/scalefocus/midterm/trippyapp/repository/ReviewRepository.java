package com.scalefocus.midterm.trippyapp.repository;

import com.scalefocus.midterm.trippyapp.model.Review;
import com.scalefocus.midterm.trippyapp.model.dto.ReviewDto;

import java.util.List;

public interface ReviewRepository extends CustomRepository<Review> {
    ReviewDto delete(Review review);

    List<Review> getReviewsByUsername(String username);

    List<Review> getReviewsByBusinessId(Long id);
}
