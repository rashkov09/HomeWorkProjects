package com.scalefocus.midterm.trippyapp.model;

import java.time.LocalDate;
import java.util.List;

public class User extends BaseEntity{
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String city;
    private LocalDate joiningDate;
    private List<Review> reviewList;

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCity() {
        return city;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }
}
