package com.scalefocus.midterm.trippyapp.model.dto;

import com.scalefocus.midterm.trippyapp.constants.enums.BusinessType;

public class BusinessDto {
    private Long id;
    private String name;
    private String city;
    private BusinessType businessType;
    private Integer numberOfReviews;
    private Double averageRating;
    private String address;
    private String email;
    private String phone;
    private String website;

    public BusinessDto() {
    }

    public BusinessDto(Long id, String name, String city, BusinessType businessType, Integer numberOfReviews, Double averageRating, String address, String email, String phone, String website) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.businessType = businessType;
        this.numberOfReviews = numberOfReviews;
        this.averageRating = averageRating;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.website = website;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public BusinessType getBusinessType() {
        return businessType;
    }

    public void setBusinessType(BusinessType businessType) {
        this.businessType = businessType;
    }

    public Integer getNumberOfReviews() {
        return numberOfReviews;
    }

    public void setNumberOfReviews(Integer numberOfReviews) {
        this.numberOfReviews = numberOfReviews;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
