package com.slm.springlibrarymanagement.repository.impl;

import com.slm.springlibrarymanagement.constants.ClassesEnum;
import com.slm.springlibrarymanagement.exceptions.book.BookNotFoundException;
import com.slm.springlibrarymanagement.exceptions.client.ClientNotFoundException;
import com.slm.springlibrarymanagement.mappers.BookMapper;
import com.slm.springlibrarymanagement.mappers.ClientMapper;
import com.slm.springlibrarymanagement.model.dto.BookDto;
import com.slm.springlibrarymanagement.model.entities.Author;
import com.slm.springlibrarymanagement.model.entities.Book;
import com.slm.springlibrarymanagement.model.entities.Client;
import com.slm.springlibrarymanagement.model.entities.Order;
import com.slm.springlibrarymanagement.service.dmo.impl.DataLoaderServiceImpl;
import com.slm.springlibrarymanagement.service.dmo.impl.DataWriterServiceImpl;
import com.slm.springlibrarymanagement.service.impl.BookServiceImpl;
import com.slm.springlibrarymanagement.service.impl.ClientServiceImpl;
import com.slm.springlibrarymanagement.util.CustomDateFormatter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
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
public class OrderRepositoryImplTest {
    private final List<Order> orderList = new ArrayList<>();
    @Mock
    private DataLoaderServiceImpl<Order> orderDataLoaderService;
    @Mock
    private DataWriterServiceImpl<Order> orderDataWriterService;
    @Mock
    private ClientServiceImpl clientService;
    @Mock
    private BookServiceImpl bookService;
    @Spy
    private BookMapper bookMapper;
    @Spy
    private ClientMapper clientMapper;
    @Mock
    private CustomDateFormatter formatter;
    @InjectMocks
    private OrderRepositoryImpl orderRepository;

    @Before
    public void setUp() throws SQLException, ClientNotFoundException, BookNotFoundException {
        Client client = new Client();
        client.setId(1L);
        client.setFirstName("Damon");
        client.setLastName("Sgorish");
        client.setAddress("Russia");
        client.setPhoneNumber("+35345665555");
        Author author = new Author();
        author.setName("Dali Rim");
        author.setId(1L);
        BookDto book = new BookDto(1L, "The Book",author,LocalDate.now(),4);
        Order order = new Order();
        order.setClient(client);
        order.setBook(bookMapper.mapFromDto(book));
        order.setIssueDate(LocalDate.of(1800, 1, 1));
        order.setBookCount(2);
        order.setId(1L);
        orderList.add(order);
        when(orderDataLoaderService.loadDataFromFile(ClassesEnum.Order)).thenReturn(orderList);
        when(clientService.findClientByFullName(client.fullName())).thenReturn(client);
        when( bookService.findBookByName(book.getName())).thenReturn(book);
        orderRepository.loadOrderData();
    }

    @Test
    public void testFindAllOrders() {
        List<Order> current = orderRepository.findAllOrders();
        Assert.assertTrue(current.size() > 0);
        Assert.assertEquals("Damon", current.get(0).getClient().getFirstName());
        Assert.assertEquals("The Book", current.get(0).getBook().getName());
    }

    @Test
    public void testFindOrdersByClientIdReturnsEmptyIfNotFound() {
        List<Order> current = orderRepository.findOrdersByClientId(99L);
        Assert.assertTrue(current.isEmpty());
    }

    ;

    @Test
    public void testFindOrdersByClientIdReturnsValid() {
        List<Order> current = orderRepository.findOrdersByClientId(1L);
        Assert.assertEquals(1, current.size());
        Assert.assertEquals("Damon", current.get(0).getClient().getFirstName());
        Assert.assertEquals("The Book", current.get(0).getBook().getName());
    }

    @Test
    public void findOrdersByIssueDateReturnsValid() {
        when(formatter.getFormatter()).thenReturn(new CustomDateFormatter().getFormatter());
        List<Order> current = orderRepository.findOrdersByIssueDate("01/01/1800");
        Assert.assertEquals(1, current.size());
        Assert.assertEquals("Damon", current.get(0).getClient().getFirstName());
        Assert.assertEquals("The Book", current.get(0).getBook().getName());
    }

    @Test
    public void findOrdersByIssueDateReturnsEmpty() {
        when(formatter.getFormatter()).thenReturn(new CustomDateFormatter().getFormatter());
        List<Order> current = orderRepository.findOrdersByIssueDate("01/01/1200");
        Assert.assertTrue(current.isEmpty());
    }


    @Test
    public void findOrdersWithIssueDateBeforeReturnsEmpty() {
        when(formatter.getFormatter()).thenReturn(new CustomDateFormatter().getFormatter());
        List<Order> current = orderRepository.findOrdersWithIssueDateBefore("01/01/1200");
        Assert.assertTrue(current.isEmpty());
    }

    @Test
    public void findOrdersWithIssueDateBeforeReturnsValid() {
        when(formatter.getFormatter()).thenReturn(new CustomDateFormatter().getFormatter());
        List<Order> current = orderRepository.findOrdersWithIssueDateBefore("01/01/1900");
        Assert.assertEquals(1, current.size());
        Assert.assertEquals("Damon", current.get(0).getClient().getFirstName());
        Assert.assertEquals("The Book", current.get(0).getBook().getName());
    }

    @Test
    public void findOrdersWithIssueDateAfterReturnsEmpty() {
        when(formatter.getFormatter()).thenReturn(new CustomDateFormatter().getFormatter());
        List<Order> current = orderRepository.findOrdersWithIssueDateAfter("01/01/2400");
        Assert.assertTrue(current.isEmpty());
    }

    @Test
    public void findOrdersWithIssueDateAfterReturnsValid() {
        when(formatter.getFormatter()).thenReturn(new CustomDateFormatter().getFormatter());
        List<Order> current = orderRepository.findOrdersWithIssueDateAfter("01/01/1799");
        Assert.assertEquals(1, current.size());
        Assert.assertEquals("Damon", current.get(0).getClient().getFirstName());
        Assert.assertEquals("The Book", current.get(0).getBook().getName());
    }

    @Test(expected = NoSuchElementException.class)
    public void findOrderByIdThrowsException() {
        orderRepository.findOrderById(99L);
    }

    @Test
    public void findOrderByIdReturnsValid() {
        Order order = orderRepository.findOrderById(1L);
        Assert.assertEquals("Damon", order.getClient().getFirstName());
        Assert.assertEquals("The Book", order.getBook().getName());
    }

    @Test
    public void addOrder() {
        Order order = new Order();
        orderRepository.addOrder(order);
        verify(orderDataWriterService).save(any(), any());
    }
}