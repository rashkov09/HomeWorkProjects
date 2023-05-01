package com.scalefocus.midterm.trippyapp.testutils.Review;

import com.scalefocus.midterm.trippyapp.constants.enums.ReviewRating;
import com.scalefocus.midterm.trippyapp.controller.request.ReviewRequest;
import com.scalefocus.midterm.trippyapp.model.Review;
import com.scalefocus.midterm.trippyapp.model.dto.ReviewDto;

import static com.scalefocus.midterm.trippyapp.testutils.Review.ReviewConstants.*;

public class ReviewFactory {

    private ReviewFactory() {
        throw new IllegalStateException();
    }

    public static Review getDefaultReview(){
        return new Review(REVIEW_ID,REVIEW_USERNAME,ReviewRating.values()[Integer.parseInt(REVIEW_RATING)-1],REVIEW_TEXT,REVIEW_BUSINESS);
    }

    public static ReviewDto getDefaultReviewDto(){
        return new ReviewDto(REVIEW_ID,REVIEW_USERNAME, REVIEW_STAMP_CREATED,ReviewRating.values()[Integer.parseInt(REVIEW_RATING)-1],REVIEW_TEXT,REVIEW_BUSINESS);
    }

    public static ReviewRequest getDefaultReviewRequest(){
        return new ReviewRequest(REVIEW_RATING,REVIEW_TEXT,REVIEW_BUSINESS.getId());
    }
}
