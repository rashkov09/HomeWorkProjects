package com.scalefocus.midterm.trippyapp.model;

import com.scalefocus.midterm.trippyapp.constants.enums.ReviewRating;

import java.time.LocalDate;

public class Review extends BaseEntity {
    private String username;
    private LocalDate createdOn;
    private ReviewRating rating;
    private String text;

    public Review() {
    }

    public Review(Long id, String username, LocalDate createdOn, ReviewRating rating, String text) {
        super(id);
        this.username = username;
        this.createdOn = createdOn;
        this.rating = rating;
        this.text = text;
    }

    public String getUsername() {
        return username;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public ReviewRating getRating() {
        return rating;
    }

    public String getText() {
        return text;
    }
}
