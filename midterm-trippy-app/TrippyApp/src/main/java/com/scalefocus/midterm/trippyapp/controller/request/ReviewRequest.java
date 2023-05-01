package com.scalefocus.midterm.trippyapp.controller.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class ReviewRequest {

    @Pattern(regexp = "^[1-5]$", message = "Rating not valid must be between 1 and 5")
    private String rating;
    private String text;

    @NotNull
    private Long businessId;

    public ReviewRequest() {
    }

    public ReviewRequest(String rating, String text, Long businessId) {

        this.rating = rating;
        this.text = text;
        this.businessId = businessId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
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
