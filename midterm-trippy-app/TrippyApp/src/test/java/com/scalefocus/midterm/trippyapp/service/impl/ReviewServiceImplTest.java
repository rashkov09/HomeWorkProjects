package com.scalefocus.midterm.trippyapp.service.impl;

import com.scalefocus.midterm.trippyapp.constants.enums.ReviewRating;
import com.scalefocus.midterm.trippyapp.exception.BusinessExceptions.BusinessNotFoundException;
import com.scalefocus.midterm.trippyapp.exception.NoDataFoundException;
import com.scalefocus.midterm.trippyapp.exception.ReviewExceptions.ReviewAccessDeniedException;
import com.scalefocus.midterm.trippyapp.exception.ReviewExceptions.ReviewNotFoundException;
import com.scalefocus.midterm.trippyapp.mapper.BusinessMapper;
import com.scalefocus.midterm.trippyapp.mapper.ReviewMapper;
import com.scalefocus.midterm.trippyapp.model.Review;
import com.scalefocus.midterm.trippyapp.model.dto.ReviewDto;
import com.scalefocus.midterm.trippyapp.repository.ReviewRepository;
import com.scalefocus.midterm.trippyapp.service.BusinessService;
import com.scalefocus.midterm.trippyapp.util.ObjectChecker;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static com.scalefocus.midterm.trippyapp.testutils.Business.BusinessFactory.getDefaultBusiness;
import static com.scalefocus.midterm.trippyapp.testutils.Business.BusinessFactory.getDefaultBusinessDto;
import static com.scalefocus.midterm.trippyapp.testutils.Review.ReviewConstants.*;
import static com.scalefocus.midterm.trippyapp.testutils.Review.ReviewFactory.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReviewServiceImplTest {
    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReviewMapper reviewMapper;

    @Mock
    private ObjectChecker<Review> reviewObjectChecker;
    @Mock
    private BusinessMapper businessMapper;
    @Mock
    private BusinessService businessService;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Test
    public void getAllReviews_noException_success() {

        when(businessService.getBusinessById(any())).thenReturn(getDefaultBusinessDto());
        when(businessMapper.mapFromDto(any())).thenReturn(getDefaultBusiness());
        when(reviewRepository.getAll()).thenReturn(Collections.singletonList(getDefaultReview()));
        List<ReviewDto> reviewDtos = reviewService.getAllReviews();
        Assert.assertEquals(1, reviewDtos.size());
    }

    @Test
    public void getAllReviewsByUsername_noException_success() {
        when(businessMapper.mapFromDto(any())).thenReturn(getDefaultBusiness());
        when(reviewRepository.getReviewsByUsername(any())).thenReturn(Collections.singletonList(getDefaultReview()));
        List<Review> reviewDtos = reviewService.getReviewsByUsername(REVIEW_USERNAME);
        Assert.assertEquals(REVIEW_ID, reviewDtos.get(0).getId());
        Assert.assertEquals(REVIEW_USERNAME, reviewDtos.get(0).getUsername());
        Assert.assertEquals(ReviewRating.values()[Integer.parseInt(REVIEW_RATING) - 1], reviewDtos.get(0).getRating());
        Assert.assertEquals(1, reviewDtos.size());
    }

    @Test
    public void createReview_noException_success() throws Exception {
        when(reviewRepository.add(any())).thenReturn(REVIEW_ID);
        when(reviewObjectChecker.checkForMissingFields(any())).thenReturn(false);
        when(businessService.getBusinessById(any())).thenReturn(getDefaultBusinessDto());
        when(businessMapper.mapFromDto(any())).thenReturn(getDefaultBusiness());
        when(reviewMapper.mapFromRequest(any())).thenReturn(getDefaultReview());
        Long id = reviewService.createReview(getDefaultReviewRequest(), REVIEW_USERNAME);
        Assert.assertEquals(REVIEW_ID, id);
    }

    @Test
    public void updateReview_noException_success() throws Exception {
        Review updateReview = getDefaultReview();
        updateReview.setRating(ReviewRating.VERY_GOOD);
        ReviewDto updatedReviewDto = getDefaultReviewDto();
        updatedReviewDto.setRating(ReviewRating.VERY_GOOD);
        when(reviewMapper.mapFromRequest(any())).thenReturn(updateReview);
        when(reviewRepository.edit(any(), any())).thenReturn(updateReview);
        when(businessService.getBusinessById(any())).thenReturn(getDefaultBusinessDto());
        when(businessMapper.mapFromDto(any())).thenReturn(getDefaultBusiness());
        when(reviewMapper.mapToDto(any())).thenReturn(updatedReviewDto);
        when(reviewRepository.getById(any())).thenReturn(getDefaultReview());
        ReviewDto reviewDto = reviewService.editReview(getDefaultReviewRequest(), REVIEW_ID.intValue(), REVIEW_USERNAME);
        assertEquals(reviewDto.getRating(), updateReview.getRating());

    }

    @Test
    public void deleteReview_noException_success() {
        when(reviewRepository.getById(any())).thenReturn(getDefaultReview());
        when(reviewRepository.delete(any())).thenReturn(getDefaultReviewDto());
        when(businessService.getBusinessById(any())).thenReturn(getDefaultBusinessDto());
        when(businessMapper.mapFromDto(any())).thenReturn(getDefaultBusiness());
        ReviewDto reviewDto = reviewService.deleteReview(REVIEW_ID.intValue(), REVIEW_USERNAME);
        Assert.assertEquals(REVIEW_ID, reviewDto.getId());
        Assert.assertEquals(REVIEW_USERNAME, reviewDto.getUsername());
        Assert.assertEquals(REVIEW_TEXT, reviewDto.getText());
    }


    @Test(expected = BusinessNotFoundException.class)
    public void createReview_withException_throws() {
        when(businessService.getBusinessById(any())).thenReturn(null);
        reviewService.createReview(getDefaultReviewRequest(), REVIEW_USERNAME);
    }

    @Test(expected = ReviewNotFoundException.class)
    public void editReviewNotFound_withException_throws() {
        reviewService.editReview(getDefaultReviewRequest(), REVIEW_ID.intValue(), REVIEW_USERNAME);
    }

    @Test(expected = ReviewAccessDeniedException.class)
    public void editReviewAccessDenied_withException_throws() {
        Review review = getDefaultReview();
        review.setUsername("other");
        when(reviewRepository.getById(any())).thenReturn(review);

        reviewService.editReview(getDefaultReviewRequest(), REVIEW_ID.intValue(), REVIEW_USERNAME);
    }

    @Test(expected = ReviewNotFoundException.class)
    public void deleteReviewNotFound_withException_throws() {
        when(reviewRepository.getById(any())).thenReturn(null);
        reviewService.deleteReview(REVIEW_ID.intValue(), REVIEW_USERNAME);
    }

    @Test(expected = ReviewAccessDeniedException.class)
    public void deleteReviewAccessDenied_withException_throws() {
        Review review = getDefaultReview();
        review.setUsername("other");
        when(reviewRepository.getById(any())).thenReturn(review);

        reviewService.deleteReview(REVIEW_ID.intValue(), REVIEW_USERNAME);
    }

    @Test(expected = ReviewNotFoundException.class)
    public void getById_withException_throws() {
        when(reviewRepository.getById(any())).thenReturn(null);

        reviewService.getReviewById(REVIEW_ID);
    }

    @Test(expected = NoDataFoundException.class)
    public void getByAll_withException_throws() {
        when(reviewRepository.getAll()).thenReturn(Collections.emptyList());
        reviewService.getAllReviews();
    }


}