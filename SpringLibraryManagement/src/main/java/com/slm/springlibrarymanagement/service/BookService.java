package com.slm.springlibrarymanagement.service;

import com.slm.springlibrarymanagement.controller.request.BookRequest;
import com.slm.springlibrarymanagement.exceptions.BackUpFailedException;
import com.slm.springlibrarymanagement.exceptions.author.AuthorNotFoundException;
import com.slm.springlibrarymanagement.exceptions.author.InvalidAuthorNameException;
import com.slm.springlibrarymanagement.exceptions.book.BookNotFoundException;
import com.slm.springlibrarymanagement.model.dto.BookDto;
import com.slm.springlibrarymanagement.model.entities.Book;

import java.sql.SQLException;
import java.util.List;

public interface BookService {

    List<BookDto> findAllBooks();

    Book insertBook(BookRequest bookRequest);

    void backupToFile() throws BackUpFailedException;

    BookDto findBookByName(String bookName) throws BookNotFoundException;

    Book findBookByIssueDate(String issueDate) throws BookNotFoundException;

    String findBooksByAuthorName(String authorName) throws BookNotFoundException, AuthorNotFoundException, InvalidAuthorNameException;

    String findBooksByNameStartingWith(String prefix) throws BookNotFoundException;

    BookDto findBookById(Long bookId) throws BookNotFoundException;

    void updateBook(Book book) throws SQLException;

}
