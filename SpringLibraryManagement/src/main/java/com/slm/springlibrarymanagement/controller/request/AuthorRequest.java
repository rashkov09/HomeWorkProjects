package com.slm.springlibrarymanagement.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Pattern;

public class AuthorRequest {
    @Pattern(regexp = "[A-Za-z\\s]+", message = "Name must not be null or contain numbers")
    private String name;


    public AuthorRequest(String name) {
        this.name = name;
    }

    public AuthorRequest() {
    }


    public String getName() {
        return name;
    }
}
