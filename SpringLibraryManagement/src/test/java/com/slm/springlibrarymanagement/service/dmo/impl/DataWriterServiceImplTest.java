package com.slm.springlibrarymanagement.service.dmo.impl;

import com.slm.springlibrarymanagement.accessor.AuthorFileAccessor;
import com.slm.springlibrarymanagement.accessor.BookFileAccessor;
import com.slm.springlibrarymanagement.accessor.ClientFileAccessor;
import com.slm.springlibrarymanagement.accessor.OrderFileAccessor;
import com.slm.springlibrarymanagement.model.entities.Author;
import com.slm.springlibrarymanagement.model.entities.Book;
import com.slm.springlibrarymanagement.service.dmo.DataWriterService;
import com.slm.springlibrarymanagement.util.CustomDateFormatter;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DataWriterServiceImplTest {
    @Mock
    public DataSource dataSource ;
    @Mock
    public AuthorFileAccessor authorFileAccessor;
    @Mock
    public BookFileAccessor bookFileAccessor;
    @Mock
    public ClientFileAccessor clientFileAccessor;
    @Mock
    public OrderFileAccessor orderFileAccessor;
    @Mock
    public CustomDateFormatter formatter;

    @InjectMocks
    public DataWriterServiceImpl<Author> authorDataWriterService;

    @InjectMocks
    public DataWriterServiceImpl<Book> bookDataWriterService;


    @Test
    public void save_Author_Successful() throws SQLException {

        String sql = "INSERT INTO slm.authors (name) VALUES (?)";

        Author author = new Author();
        author.setName("John Doe");

        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong(1)).thenReturn(1L);

        Long result = authorDataWriterService.save(sql, author);

        Assertions.assertEquals(1L, result);
        verify(preparedStatement, times(1)).setString(1, "John Doe");
        verify(preparedStatement, times(1)).executeUpdate();
        verify(preparedStatement, times(1)).getGeneratedKeys();
    }

    @Test
    public void save_Book_Successful() throws SQLException {
        DataWriterServiceImpl<Book> dataWriterService = new DataWriterServiceImpl<>(dataSource,
                authorFileAccessor, bookFileAccessor, formatter, clientFileAccessor, orderFileAccessor);

        String sql = "INSERT INTO slm.books (name, author, issue_date, number_of_copies) VALUES(?,?,?,?)";

        Author author = new Author();
        author.setName("John Doe");

        Book book = new Book();
        book.setId(1L);
        book.setAuthor(author);
        book.setIssueDate(LocalDate.now());
        book.setNumberOfCopies(4);

        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong(1)).thenReturn(1L);

        Long result = bookDataWriterService.save(sql, book);

        Assertions.assertEquals(1L, result);
        verify(preparedStatement, times(1)).setString(1, book.getName());
        verify(preparedStatement, times(1)).setObject(2, book.getAuthor().getId());
        verify(preparedStatement, times(1)).setDate(3, Date.valueOf(book.getIssueDate()));
        verify(preparedStatement, times(1)).setInt(4, book.getNumberOfCopies());
        verify(preparedStatement, times(1)).executeUpdate();
        verify(preparedStatement, times(1)).getGeneratedKeys();
    }

    @Test
    public void saveAll() {
    }

    @Test
    public void writeDataToFile() {
    }

    @Test
    public void update() {
    }
}