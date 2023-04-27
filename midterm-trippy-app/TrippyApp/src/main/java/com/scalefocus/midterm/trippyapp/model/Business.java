package com.scalefocus.midterm.trippyapp.model;

public class Business extends BaseEntity {
    private String name;
    private String city;
    private Integer numberOfReviews;
    private Double averageRate;
    private String address;
    private String email;
    private String phone;
    private String website;

    public Business() {
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public Integer getNumberOfReviews() {
        return numberOfReviews;
    }

    public Double getAverageRate() {
        return averageRate;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }
}
