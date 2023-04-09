package com.slm.springlibrarymanagement.service.impl;

import com.slm.springlibrarymanagement.accessor.BookFileAccessor;
import com.slm.springlibrarymanagement.exceptions.BackUpFailedException;
import com.slm.springlibrarymanagement.exceptions.FileForEntityNotFound;
import com.slm.springlibrarymanagement.exceptions.InvalidIdException;
import com.slm.springlibrarymanagement.exceptions.NoEntriesFoundException;
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
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class BookServiceImpl implements BookService {

    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
    private final static String UNEXPECTED_EXCEPTION_MESSAGE = "This is not expected! Invalid author name in file!";
    private final static String BOOK_TEMPLATE = """
            --------------------------------------------------
            | Book ID: %d
            | Book name: %s
            | Written by: %s
            | Issued on: %s
            | Copies: %d
            --------------------------------------------------
            """;
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final BookFileAccessor bookFileAccessor;

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, BookFileAccessor bookFileAccessor) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.bookFileAccessor = bookFileAccessor;
    }

    @Override
    public void importBooks() throws FileForEntityNotFound {
        try {
            findAllBooks();
        } catch (NoEntriesFoundException e) {
            List<Book> bookList = new ArrayList<>();
            try {
                bookFileAccessor.readAllLines().forEach(line -> {
                    Book book = new Book();
                    setBookData(line, book);
                    bookList.add(book);
                });
            } catch (Exception ex) {
                throw new FileForEntityNotFound();
            }
            bookRepository.saveAll(bookList);
        }
    }

    private void setBookData(String line, Book book) {
        Author author;
        String[] splitBookData = line.split("_");
        if (splitBookData[0].contains(".")) {
            String[] splitId = splitBookData[0].split("\\.", 2);
            book.setName(splitId[1]);
            book.setIssueDate(LocalDate.parse(splitBookData[2]));
        } else {
            book.setName(splitBookData[0]);
            book.setIssueDate(LocalDate.parse(splitBookData[2], formatter));
        }
        try {
            authorService.insertAuthor(splitBookData[1]);
        } catch (AuthorAlreadyExistsException a) {
            try {
                author = authorService.findAuthorByName(splitBookData[1]);
                book.setAuthor(author);

            } catch (Exception e1) {
                throw new RuntimeException(UNEXPECTED_EXCEPTION_MESSAGE);
            }
        } catch (InvalidAuthorNameException a1) {
            throw new RuntimeException(UNEXPECTED_EXCEPTION_MESSAGE);
        }


        book.setNumberOfCopies(1);
    }

    @Override
    public String findAllBooks() throws NoEntriesFoundException {
        StringBuilder builder = new StringBuilder();
        bookRepository.findAll().forEach(book -> builder.append(String.format(BOOK_TEMPLATE, book.getId(), book.getName(), book.getAuthor().getName(), book.getIssueDate(), book.getNumberOfCopies())));
        if (builder.toString().isEmpty() || builder.toString().isBlank()) {
            throw new NoEntriesFoundException();
        }

        return builder.toString();
    }

    @Override
    public String insertBook(String authorId, String bookName, String issueDate, String numberOfCopies) throws InvalidIdException, AuthorNotFoundException, InvalidNumberOfCopies {
        StringBuilder builder = new StringBuilder();
        Book book = bookRepository.findByName(bookName);
        try {
            Integer.parseInt(numberOfCopies);
        } catch (NumberFormatException e) {
            throw new InvalidNumberOfCopies();
        }
        if (book == null) {
            book = new Book();
            try {
                Author author = authorService.findAuthorById(authorId);
                book.setAuthor(author);
                book.setName(bookName);
                book.setIssueDate(LocalDate.parse(issueDate, formatter));
                book.setNumberOfCopies(1);
                bookRepository.save(book);
                builder.append(String.format("Book %s added successfully!", book.getName()));
            } catch (InvalidIdException e) {
                throw new InvalidIdException();
            } catch (AuthorNotFoundException a) {
                throw new AuthorNotFoundException();
            }
        } else {
            book.addCopies(Integer.parseInt(numberOfCopies));
            bookRepository.save(book);
            builder.append(String.format("%s copies of %s added successfully!", numberOfCopies, book.getName()));
        }
        return builder.toString();
    }

    @Override
    public void backupToFile() throws BackUpFailedException {
        StringBuilder builder = new StringBuilder();
        bookRepository.findAll().forEach(book -> builder
                .append(String.format("%d.%s_%s_%s", book.getId(), book.getName(), book.getAuthor().getName(), book.getIssueDate()))
                .append("\n"));

        try {
            bookFileAccessor.writeLine(builder.toString().trim());
        } catch (IOException e) {
            throw new BackUpFailedException();
        }
    }

    @Override
    public String findBookByName(String bookName) throws BookNotFoundException {
        Book book = bookRepository.findByName(bookName);
        if (book == null) {
            throw new BookNotFoundException();
        }
        return String.format(BOOK_TEMPLATE, book.getId(), book.getName(), book.getAuthor().getName(), formatter.format(book.getIssueDate()), book.getNumberOfCopies());
    }

    @Override
    public String findBookByIssueDate(String issueDate) throws BookNotFoundException {
        Book book = bookRepository.findByIssueDate(LocalDate.parse(issueDate, formatter));
        if (book == null) {
            throw new BookNotFoundException();
        }
        return String.format(BOOK_TEMPLATE, book.getId(), book.getName(), book.getAuthor().getName(), book.getIssueDate(), book.getNumberOfCopies());
    }

    @Override
    public String findBooksByAuthorName(String authorName) throws AuthorNotFoundException, InvalidAuthorNameException {
        StringBuilder builder = new StringBuilder();
        Author author = authorService.findAuthorByName(authorName);
        author.getBooks().forEach(book -> builder.append(String.format(BOOK_TEMPLATE, book.getId(), book.getName(), book.getAuthor().getName(), book.getIssueDate(), book.getNumberOfCopies())));
        return builder.toString();
    }

    @Override
    public String findBooksByNameStartingWith(String prefix) throws BookNotFoundException {
        StringBuilder builder = new StringBuilder();
        Set<Book> books = bookRepository.findByNameStartingWith(prefix);
        if (books.isEmpty()) {
            throw new BookNotFoundException();
        }
        books.forEach(book -> builder.append(String.format(BOOK_TEMPLATE, book.getId(), book.getName(), book.getAuthor().getName(), book.getIssueDate(), book.getNumberOfCopies())));
        return builder.toString();
    }
}
