package com.slm.springlibrarymanagement.model.dto;

public class AuthorDto {
    private Long id;
    private String name;

    public AuthorDto(Long id,String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
