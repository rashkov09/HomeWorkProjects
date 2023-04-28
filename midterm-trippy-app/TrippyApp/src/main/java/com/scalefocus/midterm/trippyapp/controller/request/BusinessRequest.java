package com.scalefocus.midterm.trippyapp.controller.request;

import com.scalefocus.midterm.trippyapp.constants.enums.BusinessType;

public class BusinessRequest {
    private String name;
    private String city;
    private BusinessType businessType;
    private String address;
    private String email;
    private String phone;
    private String website;

    public BusinessRequest(String name, String city, BusinessType businessType, String address, String email, String phone, String website) {
        this.name = name;
        this.city = city;
        this.businessType = businessType;
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
