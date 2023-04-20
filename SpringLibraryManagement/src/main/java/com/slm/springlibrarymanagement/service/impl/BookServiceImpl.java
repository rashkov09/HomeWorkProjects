package com.slm.springlibrarymanagement.service.impl;

import com.slm.springlibrarymanagement.exceptions.BackUpFailedException;
import com.slm.springlibrarymanagement.exceptions.InvalidDateException;
import com.slm.springlibrarymanagement.exceptions.NoEntriesFoundException;
import com.slm.springlibrarymanagement.exceptions.book.BookNotFoundException;
import com.slm.springlibrarymanagement.exceptions.book.InvalidNumberOfCopies;
import com.slm.springlibrarymanagement.mappers.AuthorMapper;
import com.slm.springlibrarymanagement.mappers.BookMapper;
import com.slm.springlibrarymanagement.model.dto.BookDto;
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
import java.util.List;
import java.util.NoSuchElementException;

import static com.slm.springlibrarymanagement.constants.messages.BookMessages.*;

@Service
public class BookServiceImpl implements BookService {
    private final CustomDateFormatter formatter;
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final AuthorMapper authorMapper;
    private final BookMapper bookMapper;
    private final InputValidator inputValidator;

    @Autowired
    public BookServiceImpl(CustomDateFormatter formatter,
                           BookRepository bookRepository,
                           AuthorService authorService,
                           AuthorMapper authorMapper, BookMapper bookMapper, InputValidator inputValidator) {
        this.formatter = formatter;
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.authorMapper = authorMapper;
        this.bookMapper = bookMapper;
        this.inputValidator = inputValidator;
    }


    @Override
    public List<BookDto> findAllBooks() {
        List<BookDto> bookDtos = bookMapper.mapToDtoList(bookRepository.findAllBooks());
        if (bookDtos.isEmpty()) {
            throw new NoEntriesFoundException();
        }
        return bookDtos;
    }

    @Override
    public String insertBook(String authorId, String bookName, String issueDate, String numberOfCopies){
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
                Author author = authorMapper.mapFromDto(authorService.findAuthorById(authorId));
                book.setAuthor(author);
                book.setName(bookName);
                book.setIssueDate(LocalDate.parse(issueDate, formatter.getFormatter()));
                book.setNumberOfCopies(Integer.parseInt(numberOfCopies));
                if (bookRepository.addBook(book)) {
                    builder.append(String.format(BOOK_ADDED_SUCCESSFULLY_MESSAGE, book.getName()));
                } else {
                    builder.append(String.format(BOOK_ADDITION_FAILED_MESSAGE, book.getName()));
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
    public BookDto findBookByName(String bookName){
        try {
            return bookMapper.mapToDto(bookRepository.findByName(bookName));
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
        List<Book> books = bookRepository.findByNameStartingWith(prefix);
        if (books.isEmpty()) {
            throw new BookNotFoundException();
        }
        books.forEach(book -> builder.append(book.toString()));
        return builder.toString();
    }

    @Override
    public BookDto findBookById(Long bookId){
        try {
            return bookMapper.mapToDto(bookRepository.findById(bookId));
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
