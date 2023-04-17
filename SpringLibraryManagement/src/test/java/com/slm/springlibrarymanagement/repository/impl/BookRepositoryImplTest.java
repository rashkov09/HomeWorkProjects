package com.slm.springlibrarymanagement.repository.impl;

import com.slm.springlibrarymanagement.constants.ClassesEnum;
import com.slm.springlibrarymanagement.model.entities.Author;
import com.slm.springlibrarymanagement.model.entities.Book;
import com.slm.springlibrarymanagement.service.AuthorService;
import com.slm.springlibrarymanagement.service.dmo.impl.DataLoaderServiceImpl;
import com.slm.springlibrarymanagement.service.dmo.impl.DataWriterServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookRepositoryImplTest {
    private final List<Book> bookList = new ArrayList<>();
    @Mock
    private DataLoaderServiceImpl<Book> bookDataLoaderService;
    @Mock
    private DataWriterServiceImpl<Book> bookDataWriterService;
    @Mock
    private AuthorService authorService;
    @InjectMocks
    private BookRepositoryImpl bookRepository;

    @Before
    public void setUp() throws SQLException {
        Book book = new Book();
        book.setId(1L);
        book.setName("Avalon");
        Author author = new Author();
        author.setName("Dim Dimov");
        book.setAuthor(author);
        book.setIssueDate(LocalDate.now());
        book.setNumberOfCopies(2);
        bookList.add(book);
        when(bookDataLoaderService.loadDataFromFile(ClassesEnum.Book)).thenReturn(bookList);
        bookRepository.loadBookData();
    }

    @Test
    public void testFindAllBooks() {
        List<Book> current = bookRepository.findAllBooks();
        Assert.assertTrue(current.size() > 0);
        Assert.assertEquals(current.get(0).getName(), "Avalon");

    }


    @Test(expected = NoSuchElementException.class)
    public void testFindByName() {
        bookRepository.findByName("Not Avalon");
    }

    @Test(expected = NoSuchElementException.class)
    public void testFindByIssueDate() {
        LocalDate localDate = LocalDate.of(1800, 1, 1);
        bookRepository.findByIssueDate(localDate);
    }

    @Test
    public void testFindByNameStartingWithReturnsEmptySet() {
        String literal = "ss";
        List<Book> search = bookRepository.findByNameStartingWith(literal);
        Assert.assertTrue(search.isEmpty());
    }

    @Test
    public void testFindByNameStartingWithReturnsValidResult() {
        String literal = "ava";
        List<Book> search = bookRepository.findByNameStartingWith(literal);
        Assert.assertEquals(1, search.size());
        Assert.assertEquals("Avalon", search.get(0).getName());
    }


    @Test(expected = NoSuchElementException.class)
    public void testFindByIdThrowsException() {
        bookRepository.findById(99L);
    }

    @Test
    public void testFindByIdReturnValidResult() {
        Book book = bookRepository.findById(1L);
        Assert.assertEquals("Avalon", book.getName());
    }

    @Test
    public void addBook() {
        Book book = new Book();
        bookRepository.addBook(book);
        verify(bookDataWriterService).save(any(), any());
    }

}