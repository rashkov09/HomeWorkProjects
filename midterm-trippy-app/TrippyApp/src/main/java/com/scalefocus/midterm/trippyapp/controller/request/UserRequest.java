package com.scalefocus.midterm.trippyapp.controller.request;

import javax.validation.constraints.Pattern;

public class UserRequest {
    @Pattern(regexp = "[A-Za-z\\s]+", message = "Name must not be null or contain numbers")
    private String username;
    private String firstName;
    private String lastName;
    private String city;

    public UserRequest(String username, String firstName, String lastName, String city) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
    }

    public String getUsername() {
        return username;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
