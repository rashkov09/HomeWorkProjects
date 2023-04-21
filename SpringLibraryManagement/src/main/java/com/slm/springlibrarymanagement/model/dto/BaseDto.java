package com.slm.springlibrarymanagement.model.dto;

public class BaseDto {
    private Long id;

    public BaseDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
