package com.scalefocus.midterm.trippyapp.controller.request;


import jakarta.validation.constraints.Pattern;

public class UserRequest {
    @Pattern(regexp = "^[A-Za-z0-9]{6,}$", message = "Username must not be empty and 6 or more characters")
    private String username;

    @Pattern(regexp = "^([A-Za-z0-9]+)@([a-z]+).([a-z]){2,3}$", message = "Invalid email format")
    private String email;

    @Pattern(regexp = "^([A-Za-z]+)$", message = "First name must start with capital letter, not be empty or contain numbers")
    private String firstName;

    @Pattern(regexp = "^([A-Z][A-Za-z]+)$", message = "Last name must start with capital letter, not be empty or contain numbers")
    private String lastName;

    @Pattern(regexp = "^([A-Z][A-Za-z]+)$", message = "City name must start with capital letter, not be empty or contain numbers")
    private String city;

    public UserRequest(String username, String email, String firstName, String lastName, String city) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;

    }


    public UserRequest() {
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
}
