package com.scalefocus.midterm.trippyapp.service.impl;

import com.scalefocus.midterm.trippyapp.controller.request.ReviewRequest;
import com.scalefocus.midterm.trippyapp.exception.BusinessExceptions.BusinessNotFoundException;
import com.scalefocus.midterm.trippyapp.exception.ReviewExceptions.ReviewAccessDeniedException;
import com.scalefocus.midterm.trippyapp.exception.ReviewExceptions.ReviewNotFoundException;
import com.scalefocus.midterm.trippyapp.mapper.BusinessMapper;
import com.scalefocus.midterm.trippyapp.mapper.ReviewMapper;
import com.scalefocus.midterm.trippyapp.model.Business;
import com.scalefocus.midterm.trippyapp.model.Review;
import com.scalefocus.midterm.trippyapp.model.User;
import com.scalefocus.midterm.trippyapp.model.dto.ReviewDto;
import com.scalefocus.midterm.trippyapp.repository.impl.ReviewRepositoryImpl;
import com.scalefocus.midterm.trippyapp.service.BusinessService;
import com.scalefocus.midterm.trippyapp.service.ReviewService;
import com.scalefocus.midterm.trippyapp.util.ObjectChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private static final Logger log = LoggerFactory.getLogger(ReviewServiceImpl.class);

    private final BusinessService businessService;
    private final ReviewRepositoryImpl reviewRepositoryImpl;
    private final ObjectChecker<ReviewRequest> reviewObjectChecker;

    private final ReviewMapper reviewMapper;
    private final BusinessMapper businessMapper;

    @Autowired
    public ReviewServiceImpl(BusinessService businessService, ReviewRepositoryImpl reviewRepositoryImpl, ObjectChecker<ReviewRequest> reviewObjectChecker, ReviewMapper reviewMapper, BusinessMapper businessMapper) {
        this.businessService = businessService;
        this.reviewRepositoryImpl = reviewRepositoryImpl;
        this.reviewObjectChecker = reviewObjectChecker;
        this.reviewMapper = reviewMapper;
        this.businessMapper = businessMapper;
    }

    @Override
    public Long createReview(ReviewRequest reviewRequest, String username) {
        reviewObjectChecker.checkForMissingFields(reviewRequest);

        Review review = reviewMapper.mapFromRequest(reviewRequest);
        Business business = businessMapper.mapFromDto(businessService.getBusinessById(reviewRequest.getBusinessId()));
        if (business == null) {
            log.error("Business not found");
            throw new BusinessNotFoundException("id " + reviewRequest.getBusinessId());
        }
        User user = new User();
        user.setUsername(username);
        review.setUsername(username);
        review.setBusiness(business);
        try {
            Long id = reviewRepositoryImpl.add(review);
            log.info("Review added successfully!");
            businessService.updateBusiness(business);
            return id;
        } catch (SQLException e) {
            throw new RuntimeException("Unexpected error", e);
        }
    }

    @Override
    public ReviewDto editReview(ReviewRequest reviewRequest, Integer id) {
        return null;
    }

    @Override
    public ReviewDto deleteReview(Integer id, String username) {
        Review review = reviewRepositoryImpl.getById(id.longValue());
        if (review == null) {
            log.error(String.format("Failed to remove review with id %s. Review not found!", id));
            throw new ReviewNotFoundException();
        }
        if (!review.getUsername().equals(username)) {
            log.error(String.format("User %s tried deleting review that he does not own!", username));
            throw new ReviewAccessDeniedException();
        }
        review.setBusiness(businessMapper.mapFromDto(businessService.getBusinessById(review.getBusiness().getId())));

        return reviewRepositoryImpl.delete(review);
    }

    @Override
    public ReviewDto getReviewById(Long id) {
        return null;
    }


    @Override
    public List<ReviewDto> getAllReviews() {
        return null;
    }

    @Override
    public List<Review> getReviewsByUsername(String username) {
        List<Review> listOfReviews = reviewRepositoryImpl.getReviewsByUsername(username);
        listOfReviews.forEach(review -> review.setBusiness(businessMapper.mapFromDto(businessService.getBusinessById(review.getBusiness().getId()))));
        return listOfReviews.isEmpty() ? new ArrayList<>() : listOfReviews;
    }
}
