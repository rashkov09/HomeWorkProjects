package com.slm.springlibrarymanagement.repository;

import com.slm.springlibrarymanagement.model.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {
}
