package com.slm.springlibrarymanagement.service.impl;

import com.slm.springlibrarymanagement.accessor.BookFileAccessor;
import com.slm.springlibrarymanagement.exceptions.*;
import com.slm.springlibrarymanagement.exceptions.author.AuthorAlreadyExistsException;
import com.slm.springlibrarymanagement.exceptions.author.AuthorNotFoundException;
import com.slm.springlibrarymanagement.exceptions.author.InvalidAuthorNameException;
import com.slm.springlibrarymanagement.exceptions.book.BookNotFoundException;
import com.slm.springlibrarymanagement.exceptions.book.InvalidNumberOfCopies;
import com.slm.springlibrarymanagement.model.entities.Author;
import com.slm.springlibrarymanagement.model.entities.Book;
import com.slm.springlibrarymanagement.repository.BookRepository;
import com.slm.springlibrarymanagement.service.AuthorService;
import com.slm.springlibrarymanagement.service.BookService;
import com.slm.springlibrarymanagement.util.CustomDateFormatter;
import com.slm.springlibrarymanagement.util.InputValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class BookServiceImpl implements BookService {
    private final static String BOOK_TEMPLATE = """
            --------------------------------------------------
            | Book ID: %d
            | Book name: %s
            | Written by: %s
            | Issued on: %s
            | Copies: %d
            --------------------------------------------------
            """;
    private final CustomDateFormatter formatter;
    private final BookRepository bookRepository;
    private final AuthorService authorService;

    private final InputValidator inputValidator;

    @Autowired
    public BookServiceImpl(CustomDateFormatter formatter,
                           BookRepository bookRepository,
                           AuthorService authorService,
                           InputValidator inputValidator) {
        this.formatter = formatter;
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.inputValidator = inputValidator;
    }


    @Override
    public String findAllBooks() throws NoEntriesFoundException {
        StringBuilder builder = new StringBuilder();
        bookRepository.findAll().forEach(book -> builder.append(String.format(BOOK_TEMPLATE,
                book.getId(),
                book.getName(),
                book.getAuthor().getName(),
                book.getIssueDate(),
                book.getNumberOfCopies())));
        if (builder.toString().isEmpty() || builder.toString().isBlank()) {
            throw new NoEntriesFoundException();
        }

        return builder.toString();
    }

    @Override
    public String insertBook(String authorId, String bookName, String issueDate, String numberOfCopies) throws InvalidIdException, AuthorNotFoundException, InvalidNumberOfCopies, InvalidDateException {
        StringBuilder builder = new StringBuilder();

        try {
            Integer.parseInt(numberOfCopies);
        } catch (NumberFormatException e) {
            throw new InvalidNumberOfCopies();
        }
        if (inputValidator.isNotValidDate(issueDate)) {
            throw new InvalidDateException();
        }
        try {
            Book book = bookRepository.findByName(bookName);
            book.addCopies(Integer.parseInt(numberOfCopies));
            bookRepository.addBook(book);
            builder.append(String.format("%s copies of %s added successfully!", numberOfCopies, book.getName()));
        } catch (NoSuchElementException e) {
            Book book = new Book();
            try {
                Author author = authorService.findAuthorById(authorId);
                book.setAuthor(author);
                book.setName(bookName);
                book.setIssueDate(LocalDate.parse(issueDate, formatter.getFormatter()));
                book.setNumberOfCopies(Integer.parseInt(numberOfCopies));
                if (bookRepository.addBook(book)) {
                    builder.append(String.format("Book %s added successfully!", book.getName()));
                } else {
                    builder.append(String.format("Book %s not added!", book.getName()));
                }
            } catch (InvalidIdException i) {
                throw new InvalidIdException();
            } catch (AuthorNotFoundException a) {
                throw new AuthorNotFoundException();
            }
        }
        return builder.toString();
    }

    @Override
    public void backupToFile() throws BackUpFailedException {
        bookRepository.backupToFile();

    }

    @Override
    public String findBookByName(String bookName) throws BookNotFoundException {
        try {
            Book book = bookRepository.findByName(bookName);
            return String.format(BOOK_TEMPLATE,
                    book.getId(),
                    book.getName(),
                    book.getAuthor().getName(),
                    formatter.getFormatter().format(book.getIssueDate()),
                    book.getNumberOfCopies());
        } catch (NoSuchElementException e) {
            throw new BookNotFoundException();
        }
    }

    @Override
    public String findBookByIssueDate(String issueDate) throws BookNotFoundException {
        try {
            Book book = bookRepository.findByIssueDate(LocalDate.parse(issueDate, formatter.getFormatter()));
            return String.format(BOOK_TEMPLATE, book.getId(), book.getName(), book.getAuthor().getName(), book.getIssueDate(), book.getNumberOfCopies());
        } catch (NoSuchElementException e) {
            throw new BookNotFoundException();
        }
    }

    @Override
    public String findBooksByAuthorName(String authorName) {
        StringBuilder builder = new StringBuilder();
        bookRepository.findAll().stream().filter(book -> book.getAuthor().getName().equals(authorName)).forEach(book -> {
            builder.append(String.format(BOOK_TEMPLATE, book.getId(), book.getName(), book.getAuthor().getName(), book.getIssueDate(), book.getNumberOfCopies()));
        });

        return builder.toString();
    }

    @Override
    public String findBooksByNameStartingWith(String prefix) throws BookNotFoundException {
        StringBuilder builder = new StringBuilder();
        Set<Book> books = bookRepository.findByNameStartingWith(prefix);
        if (books.isEmpty()) {
            throw new BookNotFoundException();
        }
        books.forEach(book -> builder.append(String.format(BOOK_TEMPLATE,
                book.getId(),
                book.getName(),
                book.getAuthor().getName(),
                book.getIssueDate(),
                book.getNumberOfCopies())));
        return builder.toString();
    }

    @Override
    public Book findBookById(Long bookId) throws BookNotFoundException {
        try {
            return bookRepository.findById(bookId);
        } catch (NoSuchElementException e) {
            throw new BookNotFoundException();
        }

    }

    @Override
    public void updateBook(Book book) {
        bookRepository.addBook(book);
    }

    @Override
    public void loadBookData() throws SQLException {
        bookRepository.loadBookData();
    }
}
