package com.lma.repository;

import com.lma.model.Book;

import java.util.HashSet;
import java.util.Set;

public interface BookRepository {


    HashSet<Book> getAllBooks();

    Boolean addBook(Book book);

    void loadBookData();

    Set<Book> findBooksByAuthorName(String authorName);

    Book findBookByName(String bookName);
}