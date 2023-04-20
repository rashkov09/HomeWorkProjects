package com.slm.springlibrarymanagement.repository.impl;

import com.slm.springlibrarymanagement.constants.ClassesEnum;
import com.slm.springlibrarymanagement.exceptions.BackUpFailedException;
import com.slm.springlibrarymanagement.exceptions.InvalidIdException;
import com.slm.springlibrarymanagement.exceptions.author.AuthorNotFoundException;
import com.slm.springlibrarymanagement.exceptions.author.InvalidAuthorNameException;
import com.slm.springlibrarymanagement.mappers.AuthorMapper;
import com.slm.springlibrarymanagement.model.entities.Book;
import com.slm.springlibrarymanagement.repository.BookRepository;
import com.slm.springlibrarymanagement.service.AuthorService;
import com.slm.springlibrarymanagement.service.dmo.DataLoaderService;
import com.slm.springlibrarymanagement.service.dmo.DataWriterService;
import com.slm.springlibrarymanagement.service.dmo.impl.DataLoaderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Repository
public class BookRepositoryImpl implements BookRepository {
    private static final String INSERT_BOOK_SQL = "INSERT INTO slm.books (name, author, issue_date, number_of_copies) VALUES(?,?,?,?)";
    private static final String UPDATE_BOOK_SQL = "UPDATE slm.books SET number_of_copies=? WHERE id=?";
    private static final String SELECT_BOOKS_SQL = "SELECT * FROM slm.books";
    private static List<Book> bookList;
    public final DataLoaderService<Book> dataLoaderService;
    public final DataWriterService<Book> dataWriterService;
    public final AuthorMapper authorMapper;
    public final AuthorService authorService;


    @Autowired
    public BookRepositoryImpl(DataLoaderServiceImpl<Book> dataLoaderService, DataWriterService<Book> dataWriterService, AuthorMapper authorMapper, AuthorService authorService) {
        this.dataLoaderService = dataLoaderService;
        this.dataWriterService = dataWriterService;
        this.authorMapper = authorMapper;
        this.authorService = authorService;
        bookList = new ArrayList<>();
    }

    @Override
    public void loadBookData() throws SQLException {
        bookList = dataLoaderService.loadDataFromDb(SELECT_BOOKS_SQL, ClassesEnum.Book);
        bookList.forEach(book -> {
            try {
                book.setAuthor(authorMapper.mapFromDto(authorService.findAuthorById(String.valueOf(book.getAuthor().getId()))));
            } catch (InvalidIdException | AuthorNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        if (bookList.isEmpty()) {
            bookList = dataLoaderService.loadDataFromFile(ClassesEnum.Book);
            bookList.forEach(book -> {
                try {
                    book.setAuthor(authorService.findAuthorByName(book.getAuthor().getName()));
                } catch (AuthorNotFoundException | InvalidAuthorNameException e) {
                    throw new RuntimeException(e);
                }
            });
            addAll();
        }
    }

    @Override
    public void backupToFile() throws BackUpFailedException {
        dataWriterService.writeDataToFile(bookList.stream().sorted().collect(Collectors.toList()), ClassesEnum.Book);
    }

    private void addAll() throws SQLException {
        dataWriterService.saveAll(INSERT_BOOK_SQL, bookList, ClassesEnum.Book);
    }

    @Override
    public List<Book> findAllBooks() {
        return bookList.stream().sorted().collect(Collectors.toList());
    }

    @Override
    public Book findByName(String bookName) throws NoSuchElementException {
        return bookList.stream().filter(book -> book.getName().equalsIgnoreCase(bookName)).findFirst().orElseThrow();
    }

    @Override
    public Book findByIssueDate(TemporalAccessor issueDate) throws NoSuchElementException {
        return bookList.stream().filter(book -> book.getIssueDate().equals(issueDate)).findFirst().orElseThrow();
    }

    @Override
    public List<Book> findByNameStartingWith(String prefix) {
        return bookList.stream().filter(book -> book.getName().toLowerCase().startsWith(prefix.toLowerCase())).collect(Collectors.toList());
    }


    @Override
    public Book findById(Long bookId) throws NoSuchElementException {
        return bookList.stream().filter(book -> book.getId().equals(bookId)).findFirst().orElseThrow();
    }

    @Override
    public boolean addBook(Book book) {
        Long id = dataWriterService.save(INSERT_BOOK_SQL, book);
        if (id != 0L) {
            book.setId(id);
            bookList.add(book);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateBook(Book book) throws SQLException {
        return dataWriterService.update(UPDATE_BOOK_SQL, book, ClassesEnum.Book);
    }
}
