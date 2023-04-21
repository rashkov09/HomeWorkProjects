package com.slm.springlibrarymanagement.mappers;

import com.slm.springlibrarymanagement.model.dto.BookDto;
import com.slm.springlibrarymanagement.model.entities.Author;
import com.slm.springlibrarymanagement.model.entities.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class BookMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setId(rs.getLong("id"));
        book.setName(rs.getString("name"));
        Author author = new Author();
        author.setId(rs.getLong("author"));
        book.setAuthor(author);
        book.setIssueDate(rs.getDate("issue_date").toLocalDate());
        book.setNumberOfCopies(rs.getInt("number_of_copies"));
        return book;
    }


    public List<BookDto> mapToDtoList(List<Book> inboundList) {
        return inboundList.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public BookDto mapToDto(Book inboundBook) {
        return new BookDto(inboundBook.getId(), inboundBook.getName(), inboundBook.getAuthor(), inboundBook.getIssueDate(), inboundBook.getNumberOfCopies());
    }

    public Book mapFromDto(BookDto inboundBookDto) {
        Book book = new Book();
        book.setId(inboundBookDto.getId());
        book.setName(inboundBookDto.getName());
        book.setAuthor(inboundBookDto.getAuthor());
        book.setIssueDate(inboundBookDto.getIssueDate());
        book.setNumberOfCopies(inboundBookDto.getNumberOfCopies());
        return book;
    }
}
