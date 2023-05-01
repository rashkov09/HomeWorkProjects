package com.scalefocus.midterm.trippyapp.service.impl;

import com.scalefocus.midterm.trippyapp.controller.request.ReviewRequest;
import com.scalefocus.midterm.trippyapp.exception.BusinessExceptions.BusinessNotFoundException;
import com.scalefocus.midterm.trippyapp.exception.NoDataFoundException;
import com.scalefocus.midterm.trippyapp.exception.ReviewExceptions.ReviewAccessDeniedException;
import com.scalefocus.midterm.trippyapp.exception.ReviewExceptions.ReviewNotFoundException;
import com.scalefocus.midterm.trippyapp.mapper.BusinessMapper;
import com.scalefocus.midterm.trippyapp.mapper.ReviewMapper;
import com.scalefocus.midterm.trippyapp.model.Business;
import com.scalefocus.midterm.trippyapp.model.Review;
import com.scalefocus.midterm.trippyapp.model.User;
import com.scalefocus.midterm.trippyapp.model.dto.ReviewDto;
import com.scalefocus.midterm.trippyapp.repository.ReviewRepository;
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
import java.util.stream.Collectors;

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
    public ReviewDto editReview(ReviewRequest reviewRequest, Integer id, String username) {
        Review newReview = reviewMapper.mapFromRequest(reviewRequest);
        Review old = reviewRepository.getById(id.longValue());
        if (old == null) {
            throw new ReviewNotFoundException();
        }
        if (!old.getUsername().equals(username)) {
            throw new ReviewAccessDeniedException();
        }
        try {
            old = reviewRepository.edit(newReview, id.longValue());
            old.setBusiness(getBusiness(old.getBusiness().getId()));
            log.info(String.format("Review with id %d edited successfully!", id));
            businessService.updateBusiness(getBusiness(old.getBusiness().getId()));
            return reviewMapper.mapToDto(old);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public ReviewDto deleteReview(Integer id, String username) {
        Review review = reviewRepository.getById(id.longValue());
        if (review == null) {
            log.error(String.format("Failed to remove review with id %s. Review not found!", id));
            throw new ReviewNotFoundException();
        }
        if (!review.getUsername().equals(username)) {
            log.error(String.format("User %s tried deleting review that he does not own!", username));
            throw new ReviewAccessDeniedException();
        }
        review.setBusiness(getBusiness(review.getBusiness().getId()));

        return reviewRepository.delete(review);
    }

    @Override
    public ReviewDto getReviewById(Long id) {
        Review review = reviewRepository.getById(id);
        if (review == null) {
            log.error(String.format("Review with id %d not found!", id));
            throw new ReviewNotFoundException();
        }
        review.setBusiness(getBusiness(review.getBusiness().getId()));
        return reviewMapper.mapToDto(review);
    }

    private Business getBusiness(Long id) {
        return businessMapper.mapFromDto(businessService.getBusinessById(id));
    }


    @Override
    public List<ReviewDto> getAllReviews() {
        List<Review> reviews = reviewRepository.getAll();
        if (reviews.isEmpty()) {
            throw new NoDataFoundException();
        }
        reviews.forEach(review -> review.setBusiness(getBusiness(review.getBusiness().getId())));
        return reviews.stream().map(reviewMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<Review> getReviewsByUsername(String username) {
        List<Review> listOfReviews = reviewRepository.getReviewsByUsername(username);
        listOfReviews.forEach(review -> review.setBusiness(getBusiness(review.getBusiness().getId())));
        return listOfReviews.isEmpty() ? new ArrayList<>() : listOfReviews;
    }
}
