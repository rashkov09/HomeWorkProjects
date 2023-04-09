package com.slm.springlibrarymanagement.repository;

import com.slm.springlibrarymanagement.model.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query("select a from Author a")
    List<Author> findAll();


    Author findByName(String authorName);
}
