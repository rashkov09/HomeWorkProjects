package com.scalefocus.midterm.trippyapp.model;

import com.scalefocus.midterm.trippyapp.constants.enums.BusinessType;

public class Business extends BaseEntity {
    private String name;
    private String city;
    private BusinessType businessType;
    private Integer numberOfReviews;
    private Double averageRate;
    private String address;
    private String email;
    private String phone;
    private String website;

    public Business() {
    }

    public Business(Long id, String name, String city, BusinessType businessType, String address, String email, String phone, String website) {
        super(id);
        this.name = name;
        this.city = city;
        this.businessType = businessType;
        this.numberOfReviews = 0;
        this.averageRate = 0.00;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.website = website;
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

    public Double getAverageRate() {
        return averageRate;
    }

    public void setAverageRate(Double averageRate) {
        this.averageRate = averageRate;
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
