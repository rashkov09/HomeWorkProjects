package com.lma.service;

import com.lma.model.Book;

import java.util.Set;

public interface BookService {

    void seedBooks();
    String addBook(String name, String authorName, String publishDate);

    Book getBook(String name);

    String getAllBooks();

    Set<Book> findBooksByAuthorName(String authorName);

    String findBooksStartingWith(String value);

    String getBookByName(String subValue);

    String getBooksByAuthorName(String bookName);

    String getBooksByIssueDate(String issueDate);

    boolean removeBook(Book book);

    boolean hasAvailableBooks();

}
