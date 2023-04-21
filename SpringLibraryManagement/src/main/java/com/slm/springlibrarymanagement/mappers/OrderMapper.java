package com.slm.springlibrarymanagement.mappers;

import com.slm.springlibrarymanagement.model.dto.OrderDto;
import com.slm.springlibrarymanagement.model.entities.Book;
import com.slm.springlibrarymanagement.model.entities.Client;
import com.slm.springlibrarymanagement.model.entities.Order;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper implements RowMapper<Order> {
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
        order.updateDueDate(rs.getDate("due_date").toLocalDate());
        order.setBookCount(rs.getInt("book_count"));
        return order;
    }

    public List<OrderDto> mapToDtoList(List<Order> inboundList) {
        return inboundList.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public OrderDto mapToDto(Order inboundOrder) {
        return new OrderDto(inboundOrder.getId(), inboundOrder.getClient(), inboundOrder.getBook(), inboundOrder.getIssueDate(), inboundOrder.getBookCount());
    }

    public Order mapFromDto(OrderDto inboundOrderDto) {
        Order order = new Order();
        order.setId(inboundOrderDto.getId());
        order.setClient(inboundOrderDto.getClient());
        order.setBook(inboundOrderDto.getBook());
        order.setIssueDate(inboundOrderDto.getIssueDate());
        order.setBookCount(inboundOrderDto.getBookCount());
        return order;
    }
}
