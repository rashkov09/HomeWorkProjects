package com.slm.springlibrarymanagement.controller.request;

public class ClientRequest {
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;

    public ClientRequest() {
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
