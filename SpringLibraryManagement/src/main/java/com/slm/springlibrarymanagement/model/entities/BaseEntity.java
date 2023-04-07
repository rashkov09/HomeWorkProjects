package com.slm.springlibrarymanagement.model.entities;

import jakarta.persistence.*;

@MappedSuperclass
public class BaseEntity {
    private Long id;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }
}
