package com.scalefocus.midterm.trippyapp.controller.impl;

import com.scalefocus.midterm.trippyapp.controller.ReviewController;
import com.scalefocus.midterm.trippyapp.controller.request.ReviewRequest;
import com.scalefocus.midterm.trippyapp.model.dto.ReviewDto;
import com.scalefocus.midterm.trippyapp.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class ReviewControllerImpl implements ReviewController {

    public final ReviewService reviewService;

    public ReviewControllerImpl(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Override
    public ResponseEntity<List<ReviewDto>> getAllReviews() {
        List<ReviewDto> reviewDtos = reviewService.getAllReviews();
        return ResponseEntity.ok(reviewDtos);
    }

    @Override
    public ResponseEntity<ReviewDto> getReviewById(Long id) {
        ReviewDto reviewDto = reviewService.getReviewById(id);
        return ResponseEntity.ok(reviewDto);
    }

    @Override
    public ResponseEntity<Void> addReview(ReviewRequest reviewRequest, String username) {
        Long id = reviewService.createReview(reviewRequest, username);

        URI location = UriComponentsBuilder.fromUriString("/reviews/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<ReviewDto> updateReview(ReviewRequest reviewRequest, int id, boolean returnOld, String username) {
        ReviewDto reviewDto = reviewService.editReview(reviewRequest, id, username);
        if (returnOld) {
            return ResponseEntity.ok(reviewDto);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @Override
    public ResponseEntity<ReviewDto> deleteReview(int id, boolean returnOld, String username) {
        ReviewDto reviewDto = reviewService.deleteReview(id, username);
        if (returnOld) {
            return ResponseEntity.ok(reviewDto);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
