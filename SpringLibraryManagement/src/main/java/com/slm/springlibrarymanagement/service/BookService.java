package com.slm.springlibrarymanagement.service;

import com.slm.springlibrarymanagement.exceptions.*;
import com.slm.springlibrarymanagement.exceptions.author.AuthorNotFoundException;
import com.slm.springlibrarymanagement.exceptions.author.InvalidAuthorNameException;
import com.slm.springlibrarymanagement.exceptions.book.BookNotFoundException;
import com.slm.springlibrarymanagement.exceptions.book.InvalidNumberOfCopies;
import com.slm.springlibrarymanagement.model.entities.Book;

public interface BookService {

    void importBooks() throws FileForEntityNotFound, InvalidDateException;

    String findAllBooks() throws NoEntriesFoundException;

    String insertBook(String authorId, String bookName, String issueDate, String numberOfCopies) throws InvalidIdException, AuthorNotFoundException, InvalidNumberOfCopies, InvalidDateException;

    void backupToFile() throws BackUpFailedException;

    String findBookByName(String bookName) throws BookNotFoundException;

    String findBookByIssueDate(String issueDate) throws BookNotFoundException;

    String findBooksByAuthorName(String authorName) throws BookNotFoundException, AuthorNotFoundException, InvalidAuthorNameException;

    String findBooksByNameStartingWith(String prefix) throws BookNotFoundException;

    Book findBookById(Long bookId) throws BookNotFoundException;

    void updateBook(Book book);
}
