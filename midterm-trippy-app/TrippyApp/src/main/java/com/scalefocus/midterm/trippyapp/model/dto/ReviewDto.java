package com.scalefocus.midterm.trippyapp.model.dto;

import com.scalefocus.midterm.trippyapp.constants.enums.ReviewRating;
import com.scalefocus.midterm.trippyapp.model.Business;

import java.time.LocalDate;

public class ReviewDto {
    private Long id;
    private String username;
    private LocalDate createdOn;
    private ReviewRating rating;
    private String text;

    private Business business;

    public ReviewDto(Long id, String username, LocalDate createdOn, ReviewRating rating, String text, Business business) {
        this.id = id;
        this.username = username;
        this.createdOn = createdOn;
        this.rating = rating;
        this.text = text;
        this.business = business;
    }

    public ReviewDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
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

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }
}