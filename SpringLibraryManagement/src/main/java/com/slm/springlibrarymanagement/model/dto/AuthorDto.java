package com.slm.springlibrarymanagement.model.dto;

public class AuthorDto extends BaseDto {
    private String name;

    public AuthorDto(Long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
