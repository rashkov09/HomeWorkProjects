package com.slm.springlibrarymanagement.repository;

import com.slm.springlibrarymanagement.model.entities.Book;

import java.time.temporal.TemporalAccessor;
import java.util.List;
import java.util.Set;


public interface BookRepository {

    void loadBookData();

    List<Book> findAll();

    Book findByName(String bookName);

    Book findByIssueDate(TemporalAccessor issueDate);

    Set<Book> findByNameStartingWith(String prefix);

    void saveAll(List<Book> bookList);

    Book findById(Long bookId);

    boolean addBook(Book book);
}
