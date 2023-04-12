package com.slm.springlibrarymanagement.model.entities;

import java.util.Objects;

public class Author extends BaseEntity {
    private String name;

    public Author() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return String.format("%d. %s", this.getId(), this.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author author)) return false;
        return getName().equals(author.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
