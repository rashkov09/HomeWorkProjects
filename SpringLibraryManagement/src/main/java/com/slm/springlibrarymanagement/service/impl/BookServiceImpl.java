package com.slm.springlibrarymanagement.service.impl;

import com.slm.springlibrarymanagement.exceptions.BackUpFailedException;
import com.slm.springlibrarymanagement.exceptions.InvalidDateException;
import com.slm.springlibrarymanagement.exceptions.InvalidIdException;
import com.slm.springlibrarymanagement.exceptions.NoEntriesFoundException;
import com.slm.springlibrarymanagement.exceptions.author.AuthorNotFoundException;
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

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Set;

import static com.slm.springlibrarymanagement.constants.messages.BookMessages.*;

@Service
public class BookServiceImpl implements BookService {
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
        bookRepository.findAllBooks().forEach(book -> builder.append(book.toString()));
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

        try {
            Book book = bookRepository.findByName(bookName);
            book.addCopies(Integer.parseInt(numberOfCopies));
            builder.append(bookRepository.updateBook(book) ?
                    String.format(BOOK_COPIES_ADDED_SUCCESSFULLY_MESSAGE, numberOfCopies, book.getName()) :
                    BOOK_COPIES_ADDITION_FAILED_MESSAGE);
        } catch (NoSuchElementException e) {
            if (inputValidator.isNotValidDate(issueDate)) {
                throw new InvalidDateException();
            }
            Book book = new Book();
            try {
                Author author = authorService.findAuthorById(authorId);
                book.setAuthor(author);
                book.setName(bookName);
                book.setIssueDate(LocalDate.parse(issueDate, formatter.getFormatter()));
                book.setNumberOfCopies(Integer.parseInt(numberOfCopies));
                if (bookRepository.addBook(book)) {
                    builder.append(String.format(BOOK_ADDED_SUCCESSFULLY_MESSAGE, book.getName()));
                } else {
                    builder.append(String.format(BOOK_ADDITION_FAILED_MESSAGE, book.getName()));
                }
            } catch (InvalidIdException i) {
                throw new InvalidIdException();
            } catch (AuthorNotFoundException a) {
                throw new AuthorNotFoundException();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return builder.toString();
    }

    @Override
    public void backupToFile() throws BackUpFailedException {
        bookRepository.backupToFile();

    }

    @Override
    public Book findBookByName(String bookName) throws BookNotFoundException {
        try {
            return bookRepository.findByName(bookName);
        } catch (NoSuchElementException e) {
            throw new BookNotFoundException();
        }
    }

    @Override
    public Book findBookByIssueDate(String issueDate) throws BookNotFoundException {
        try {
            return bookRepository.findByIssueDate(LocalDate.parse(issueDate, formatter.getFormatter()));
        } catch (NoSuchElementException e) {
            throw new BookNotFoundException();
        }
    }

    @Override
    public String findBooksByAuthorName(String authorName) {
        StringBuilder builder = new StringBuilder();
        bookRepository.findAllBooks().stream().filter(book -> book.getAuthor().getName().equals(authorName)).forEach(builder::append);
        return builder.toString();
    }

    @Override
    public String findBooksByNameStartingWith(String prefix) throws BookNotFoundException {
        StringBuilder builder = new StringBuilder();
        Set<Book> books = bookRepository.findByNameStartingWith(prefix);
        if (books.isEmpty()) {
            throw new BookNotFoundException();
        }
        books.forEach(book -> builder.append(book.toString()));
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
    public void updateBook(Book book) throws SQLException {
        bookRepository.updateBook(book);
    }

    @Override
    public void loadBookData() throws SQLException {
        bookRepository.loadBookData();
    }
}
