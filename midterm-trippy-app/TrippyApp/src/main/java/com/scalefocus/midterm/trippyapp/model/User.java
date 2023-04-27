package com.scalefocus.midterm.trippyapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class User extends BaseEntity {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String city;
    private LocalDate joiningDate;
    private List<Review> reviewList;

    public User() {
        setJoiningDate(LocalDate.now());
        setReviewList(new ArrayList<>());
    }

    public User(Long id, String username, String email, String firstName, String lastName, String city) {
        super(id);
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        setJoiningDate(LocalDate.now());
        setReviewList(new ArrayList<>());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    private void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }
}
