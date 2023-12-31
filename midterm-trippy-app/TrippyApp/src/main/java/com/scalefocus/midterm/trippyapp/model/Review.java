package com.scalefocus.midterm.trippyapp.model;

import com.scalefocus.midterm.trippyapp.constants.enums.ReviewRating;

import java.time.LocalDate;

public class Review extends BaseEntity {
    private String username;
    private LocalDate createdOn;
    private ReviewRating rating;
    private String text;
    private Business business;

    public Review() {
        setCreatedOn();
    }

    public Review(Long id, String username, ReviewRating rating, String text, Business business) {
        super(id);
        this.username = username;
        setCreatedOn();
        this.rating = rating;
        this.text = text;
        this.business = business;
    }


    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    private void setCreatedOn() {
        this.createdOn = LocalDate.now();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
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
}
