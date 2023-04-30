package com.scalefocus.midterm.trippyapp.service.impl;

import com.scalefocus.midterm.trippyapp.controller.request.ReviewRequest;
import com.scalefocus.midterm.trippyapp.exception.BusinessExceptions.BusinessNotFoundException;
import com.scalefocus.midterm.trippyapp.exception.UserExceptions.UserNotFoundException;
import com.scalefocus.midterm.trippyapp.mapper.BusinessMapper;
import com.scalefocus.midterm.trippyapp.mapper.ReviewMapper;
import com.scalefocus.midterm.trippyapp.model.Business;
import com.scalefocus.midterm.trippyapp.model.Review;
import com.scalefocus.midterm.trippyapp.model.User;
import com.scalefocus.midterm.trippyapp.model.dto.ReviewDto;
import com.scalefocus.midterm.trippyapp.model.dto.UserDto;
import com.scalefocus.midterm.trippyapp.repository.impl.ReviewRepository;
import com.scalefocus.midterm.trippyapp.service.BusinessService;
import com.scalefocus.midterm.trippyapp.service.ReviewService;
import com.scalefocus.midterm.trippyapp.service.UserService;
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
    private final ReviewRepository reviewRepository;
    private final ObjectChecker<ReviewRequest> reviewObjectChecker;

    private final ReviewMapper reviewMapper;
    private final BusinessMapper businessMapper;

    @Autowired
    public ReviewServiceImpl(BusinessService businessService, ReviewRepository reviewRepository, ObjectChecker<ReviewRequest> reviewObjectChecker, ReviewMapper reviewMapper, BusinessMapper businessMapper) {
        this.businessService = businessService;
        this.reviewRepository = reviewRepository;
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
            Long id = reviewRepository.add(review);
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
    public ReviewDto deleteReview(ReviewRequest reviewRequest, Integer id) {
        return null;
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
        List<Review> listOfReviews = reviewRepository.getReviewsByUsername(username);
        listOfReviews.forEach(review -> review.setBusiness(businessMapper.mapFromDto(businessService.getBusinessById(review.getBusiness().getId()))));
        return listOfReviews.isEmpty() ?  new ArrayList<>() : listOfReviews;
    }
}
