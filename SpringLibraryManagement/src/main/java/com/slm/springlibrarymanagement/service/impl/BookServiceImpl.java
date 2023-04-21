package com.slm.springlibrarymanagement.service.impl;

import com.slm.springlibrarymanagement.controller.request.BookRequest;
import com.slm.springlibrarymanagement.exceptions.BackUpFailedException;
import com.slm.springlibrarymanagement.exceptions.InvalidDateException;
import com.slm.springlibrarymanagement.exceptions.NoEntriesFoundException;
import com.slm.springlibrarymanagement.exceptions.book.BookNotFoundException;
import com.slm.springlibrarymanagement.exceptions.book.InvalidBookNameException;
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
    public Book insertBook(BookRequest bookRequest) {

        if (bookRequest.getName().isEmpty()) {
            throw new InvalidBookNameException();
        }

        if (inputValidator.isNotValidDate(bookRequest.getIssueDate().toString())) {
            throw new InvalidDateException();
        }
        Book book = new Book();
        Author author = authorMapper.mapFromDto(authorService.findAuthorById(bookRequest.getAuthor().getId().toString()));
        book.setAuthor(author);
        book.setName(bookRequest.getName());
        book.setIssueDate(bookRequest.getIssueDate());
        book.setNumberOfCopies(bookRequest.getNumberOfCopies());
        if (bookRepository.addBook(book)) {
            return book;
        }
        return null;
    }

    @Override
    public void backupToFile() throws BackUpFailedException {
        bookRepository.backupToFile();

    }

    @Override
    public BookDto findBookByName(String bookName) {
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
    public BookDto findBookById(Long bookId) {
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

}
