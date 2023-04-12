package com.slm.springlibrarymanagement.mappers;

import com.slm.springlibrarymanagement.model.entities.Book;
import com.slm.springlibrarymanagement.model.entities.Client;
import com.slm.springlibrarymanagement.model.entities.Order;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRowMapper implements RowMapper<Order> {
    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        Order order = new Order();
        order.setId(rs.getLong("id"));
        Client client = new Client();
        client.setId(rs.getLong("client"));
        order.setClient(client);
        Book book = new Book();
        book.setId(rs.getLong("book"));
        order.setBook(book);
        order.setIssueDate(rs.getDate("issue_date").toLocalDate());
        order.setBookCount(rs.getInt("book_count"));
        return order;
    }
}
