package com.slm.springlibrarymanagement.mappers;

import com.slm.springlibrarymanagement.model.entities.Author;
import com.slm.springlibrarymanagement.model.entities.Book;
import com.slm.springlibrarymanagement.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookRowMapper implements RowMapper<Book> {

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
}
