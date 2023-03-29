package com.lma.repository;

import com.lma.model.Book;

import java.util.Set;

public interface BookRepository {


    Set<Book> getAllBooks();

    Boolean addBook(Book book);

    void loadBookData();

    Set<Book> findBooksByAuthorName(String authorName);

    Book findBookByName(String bookName);

    Set<Book> findBooksByIssueDate(String issueDate);

    boolean removeBook(Book book);
}
