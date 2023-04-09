package com.slm.springlibrarymanagement.repository;

import com.slm.springlibrarymanagement.model.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.temporal.TemporalAccessor;
import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    Book findByName(String bookName);

    Book findByIssueDate(TemporalAccessor issueDate);

    Set<Book> findByNameStartingWith(String prefix);
}
