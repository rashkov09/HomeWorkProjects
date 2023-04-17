package com.slm.springlibrarymanagement.service.dmo.impl;

import com.slm.springlibrarymanagement.accessor.AuthorFileAccessor;
import com.slm.springlibrarymanagement.accessor.BookFileAccessor;
import com.slm.springlibrarymanagement.accessor.ClientFileAccessor;
import com.slm.springlibrarymanagement.accessor.OrderFileAccessor;
import com.slm.springlibrarymanagement.model.entities.Author;
import com.slm.springlibrarymanagement.model.entities.Book;
import com.slm.springlibrarymanagement.model.entities.Client;
import com.slm.springlibrarymanagement.model.entities.Order;
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
    public DataSource dataSource;
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

    @InjectMocks
    public DataWriterServiceImpl<Client> clientDataWriterService;

    @InjectMocks
    public DataWriterServiceImpl<Order> orderDataWriterService;


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

        String sql = "INSERT INTO slm.books (name, author, issue_date, number_of_copies) VALUES(?,?,?,?)";

        Author author = new Author();
        author.setName("John Doe");

        Book book = new Book();
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
    public void save_Client_Successful() throws SQLException {

        String sql = "INSERT INTO slm.clients (first_name,last_name,address,phone_number) VALUES (?,?,?,?)";

        Client client = new Client();
        client.setFirstName("Tim");
        client.setLastName("Timson");
        client.setAddress("Sofia");
        client.setPhoneNumber("+359883474347");


        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong(1)).thenReturn(1L);

        Long result = clientDataWriterService.save(sql, client);

        Assertions.assertEquals(1L, result);

        verify(preparedStatement, times(1)).setString(1, client.getFirstName());
        verify(preparedStatement, times(1)).setString(2, client.getLastName());
        verify(preparedStatement, times(1)).setString(3, client.getAddress());
        verify(preparedStatement, times(1)).setString(4, client.getPhoneNumber());
        verify(preparedStatement, times(1)).executeUpdate();
        verify(preparedStatement, times(1)).getGeneratedKeys();
    }

    @Test
    public void save_Order_Successful() throws SQLException {

        String sql = "INSERT INTO slm.orders (client,book, issue_date,due_date,book_count) VALUES (?,?,?,?,?)";
        Client client = new Client();
        client.setFirstName("Tim");
        client.setLastName("Timson");
        client.setAddress("Sofia");
        client.setPhoneNumber("+359883474347");
        Author author = new Author();
        author.setName("Test Author");
        Book book = new Book();
        book.setName("Test book");
        book.setAuthor(author);
        book.setIssueDate(LocalDate.now());
        book.setNumberOfCopies(3);
        Order order = new Order();
        order.setBook(book);
        order.setClient(client);
        order.setIssueDate(LocalDate.now());
        order.setBookCount(4);

        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong(1)).thenReturn(1L);

        Long result = orderDataWriterService.save(sql, order);

        Assertions.assertEquals(1L, result);

        verify(preparedStatement, times(1)).setObject(1, order.getClient().getId());
        verify(preparedStatement, times(1)).setObject(2, order.getBook().getId());
        verify(preparedStatement, times(1)).setDate(3, Date.valueOf(order.getIssueDate()));
        verify(preparedStatement, times(1)).setDate(4, Date.valueOf(order.getDueDate()));
        verify(preparedStatement, times(1)).setInt(5, order.getBookCount());
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