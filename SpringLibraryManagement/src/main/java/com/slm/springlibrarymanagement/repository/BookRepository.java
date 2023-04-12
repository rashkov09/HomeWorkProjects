package com.slm.springlibrarymanagement.repository;

import com.slm.springlibrarymanagement.exceptions.BackUpFailedException;
import com.slm.springlibrarymanagement.model.entities.Book;

import java.sql.SQLException;
import java.time.temporal.TemporalAccessor;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;


public interface BookRepository {

    void loadBookData() throws SQLException;

    void backupToFile() throws BackUpFailedException;

    List<Book> findAllBooks();

    Book findByName(String bookName) throws NoSuchElementException;

    Book findByIssueDate(TemporalAccessor issueDate) throws NoSuchElementException;

    Set<Book> findByNameStartingWith(String prefix);

    Book findById(Long bookId) throws NoSuchElementException;

    boolean addBook(Book book);

    boolean updateBook(Book book) throws SQLException;
}
