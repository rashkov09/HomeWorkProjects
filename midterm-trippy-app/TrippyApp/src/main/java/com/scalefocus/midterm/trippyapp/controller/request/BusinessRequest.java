package com.scalefocus.midterm.trippyapp.controller.request;

import jakarta.validation.constraints.Pattern;

public class BusinessRequest {
    @Pattern(regexp = "^[A-Za-z0-9\\s]+$", message = "Name must not be empty")
    private String name;
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "City name must not be empty and cannot contain numbers")
    private String city;
    @Pattern(regexp = "BAR|HOTEL|RESTAURANT$", message = "Invalid type must be one of [BAR, HOTEL, RESTAURANT]")
    private String businessType;
    @Pattern(regexp = "^[A-Za-z0-9\\s]+$", message = "Address must not be empty")
    private String address;
    @Pattern(regexp = "^([A-Za-z0-9-_]+)@([a-z]+).([a-z]){2,6}$", message = "Invalid email format")
    private String email;
    @Pattern(regexp = "^([+|0])[0-9]{2,3}-[0-9]{2,3}-[0-9]{6}$", message = "Invalid phone format ex.(+359-883-879883)")
    private String phone;
    @Pattern(regexp = "^(w{3}).([a-z0-9]+).([a-z]{2,5})$", message = "Invalid website format")
    private String website;

    public BusinessRequest(String name, String city, String businessType, String address, String email, String phone, String website) {
        this.name = name;
        this.city = city;
        this.businessType = businessType;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.website = website;
    }

    public BusinessRequest() {
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

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
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
