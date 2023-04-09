package com.slm.springlibrarymanagement.repository;

import com.slm.springlibrarymanagement.model.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    Book findByName(String bookName);
}
