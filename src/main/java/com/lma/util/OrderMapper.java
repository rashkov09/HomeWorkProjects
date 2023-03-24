package com.lma.util;

import com.lma.model.Book;
import com.lma.model.Client;
import com.lma.model.Order;
import com.lma.service.BookService;
import com.lma.service.ClientService;
import com.lma.service.impl.BookServiceImpl;
import com.lma.service.impl.ClientServiceImpl;

import java.time.LocalDate;

public class OrderMapper {
private static final ClientService clientService = new ClientServiceImpl();
private static final BookService bookService = new BookServiceImpl();

    public static Order mapOrderFromString(String value) {
        String[] subValues=  value.split("_");
        Client client = clientService.getClientByFullName(subValues[0]);
        Book book = bookService.getBookByName(subValues[1]);
        LocalDate issueDate = LocalDateFormatter.stringToLocalDate(subValues[2]);
        LocalDate dueDate = LocalDateFormatter.stringToLocalDate(subValues[2]);
        return new Order(client,book,issueDate,dueDate);
    }

    public static String mapOrderToString(Order order){
        String issueDateString = LocalDateFormatter.localDateToString(order.getIssueDate());
        String dueDateString = LocalDateFormatter.localDateToString(order.getDueDate());
        return String.format("%s_%s_%s_%s",order.getClient().toString(),order.getBook().getName(),issueDateString,dueDateString);
    }
}
