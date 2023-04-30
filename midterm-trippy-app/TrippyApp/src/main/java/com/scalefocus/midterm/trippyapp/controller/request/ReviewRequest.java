package com.scalefocus.midterm.trippyapp.controller.request;

import com.scalefocus.midterm.trippyapp.constants.enums.ReviewRating;

public class ReviewRequest {


    private ReviewRating rating;
    private String text;

    private Long businessId;

    public ReviewRequest() {
    }

    public ReviewRequest(ReviewRating rating, String text, Long businessId) {

        this.rating = rating;
        this.text = text;
        this.businessId = businessId;
    }

    public ReviewRating getRating() {
        return rating;
    }

    public void setRating(ReviewRating rating) {
        this.rating = rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }
}
