package com.scalefocus.midterm.trippyapp.testutils.Review;

import com.scalefocus.midterm.trippyapp.constants.enums.BusinessType;
import com.scalefocus.midterm.trippyapp.constants.enums.ReviewRating;
import com.scalefocus.midterm.trippyapp.model.Business;

import java.time.LocalDate;

import static com.scalefocus.midterm.trippyapp.testutils.Business.BusinessFactory.getDefaultBusiness;

public final class ReviewConstants {

    public ReviewConstants() {
        throw new IllegalArgumentException();
    }

    private final static Business business = getDefaultBusiness();
    public final static Long REVIEW_ID = 1L;
    public final static String REVIEW_USERNAME = "test";
    public final static LocalDate REVIEW_STAMP_CREATED= LocalDate.of(2023,4,27);
    public final static String REVIEW_RATING= "2";
    public final static String REVIEW_TEXT= "Some test text";
    public final static Business REVIEW_BUSINESS= business;
}
