package com.lma.service;

import com.lma.model.Book;

import java.util.Set;

public interface BookService {

    void seedBooks();
    void addBook(String name, String authorName, String publishDate);

    String getAllBooks();

    Set<Book> findBooksByAuthorName(String authorName);

    Book getBookByName(String subValue);
}
