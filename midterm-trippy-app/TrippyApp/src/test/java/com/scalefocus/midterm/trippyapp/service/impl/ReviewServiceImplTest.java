package com.scalefocus.midterm.trippyapp.service.impl;

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
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static com.scalefocus.midterm.trippyapp.testutils.Business.BusinessFactory.getDefaultBusiness;
import static com.scalefocus.midterm.trippyapp.testutils.Business.BusinessFactory.getDefaultBusinessDto;
import static com.scalefocus.midterm.trippyapp.testutils.Review.ReviewConstants.*;
import static com.scalefocus.midterm.trippyapp.testutils.Review.ReviewFactory.*;
import static com.scalefocus.midterm.trippyapp.testutils.User.UserConstants.USER_ID;
import static com.scalefocus.midterm.trippyapp.testutils.User.UserConstants.USER_USERNAME;
import static com.scalefocus.midterm.trippyapp.testutils.User.UserFactory.getDefaultUser;
import static com.scalefocus.midterm.trippyapp.testutils.User.UserFactory.getDefaultUserRequest;
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
        Assert.assertEquals(REVIEW_ID,reviewDtos.get(0).getId());
        Assert.assertEquals(REVIEW_USERNAME,reviewDtos.get(0).getUsername());
        Assert.assertEquals(REVIEW_RATING,reviewDtos.get(0).getRating());
        Assert.assertEquals(1, reviewDtos.size());
    }

    @Test
    public void createReview_noException_success() throws Exception{
        when(reviewRepository.add(any())).thenReturn(REVIEW_ID);
        when(reviewObjectChecker.checkForMissingFields(any())).thenReturn(false);
        when(businessService.getBusinessById(any())).thenReturn(getDefaultBusinessDto());
        when(businessMapper.mapFromDto(any())).thenReturn(getDefaultBusiness());
        when(reviewMapper.mapFromRequest(any())).thenReturn(getDefaultReview());
        Long id = reviewService.createReview(getDefaultReviewRequest(),REVIEW_USERNAME);
        Assert.assertEquals(REVIEW_ID,id);
    }
}