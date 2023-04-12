package com.slm.springlibrarymanagement.repository.impl;

import com.slm.springlibrarymanagement.exceptions.InvalidIdException;
import com.slm.springlibrarymanagement.exceptions.author.AuthorNotFoundException;
import com.slm.springlibrarymanagement.model.entities.Book;
import com.slm.springlibrarymanagement.repository.BookRepository;
import com.slm.springlibrarymanagement.service.AuthorService;
import com.slm.springlibrarymanagement.service.dmo.DataLoaderService;
import com.slm.springlibrarymanagement.service.dmo.DataWriterService;
import com.slm.springlibrarymanagement.service.dmo.impl.DataLoaderServiceImpl;
import org.springframework.stereotype.Repository;

import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class BookRepositoryImpl implements BookRepository {
    private static List<Book> bookList;
    public final DataLoaderService<Book> dataLoaderService;
    public final DataWriterService<Book> dataWriterService;
    public final AuthorService authorService;


    public BookRepositoryImpl(DataLoaderServiceImpl<Book> dataLoaderService, DataWriterService<Book> dataWriterService, AuthorService authorService) {
        this.dataLoaderService = dataLoaderService;
        this.dataWriterService = dataWriterService;
        this.authorService = authorService;
        bookList = new ArrayList<>();
    }

    @Override
    public void loadBookData() {
        String sql = "SELECT * FROM slm.books";
        bookList = dataLoaderService.loadData(sql, new Book());
        bookList.forEach(book -> {
            try {
                book.setAuthor(authorService.findAuthorById(String.valueOf(book.getId())));
            } catch (InvalidIdException | AuthorNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public List<Book> findAll() {
        return bookList;
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
    public Set<Book> findByNameStartingWith(String prefix) {
        return bookList.stream().filter(book -> book.getName().toLowerCase().startsWith(prefix.toLowerCase())).collect(Collectors.toSet());
    }

    @Override
    public void saveAll(List<Book> bookList) {

    }

    @Override
    public Book findById(Long bookId) throws NoSuchElementException {
        return bookList.stream().filter(book -> book.getId().equals(bookId)).findFirst().orElseThrow();
    }

    @Override
    public boolean addBook(Book book) {
        String sql = "INSERT INTO slm.books (name, author, issue_date, number_of_copies) VALUES(?,?,?,?)";
        Long id = dataWriterService.save(sql, book);
        if (id != 0L) {
            book.setId(id);
            bookList.add(book);
            return true;
        }
        return false;
    }
}
