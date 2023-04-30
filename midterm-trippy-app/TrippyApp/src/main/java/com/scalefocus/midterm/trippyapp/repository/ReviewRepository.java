package com.scalefocus.midterm.trippyapp.repository;

import com.scalefocus.midterm.trippyapp.model.Review;
import com.scalefocus.midterm.trippyapp.model.dto.ReviewDto;

public interface ReviewRepository extends CustomRepository<Review> {
    ReviewDto delete(Review review);

    Review getByUsername(String username);
}
