package com.slm.springlibrarymanagement.model.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity {
    private Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "default_gen")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}