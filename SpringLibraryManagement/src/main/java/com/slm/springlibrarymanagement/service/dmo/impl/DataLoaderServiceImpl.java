package com.slm.springlibrarymanagement.service.dmo.impl;

import com.slm.springlibrarymanagement.mappers.AuthorRowMapper;
import com.slm.springlibrarymanagement.mappers.BookRowMapper;
import com.slm.springlibrarymanagement.mappers.ClientRowMapper;
import com.slm.springlibrarymanagement.model.entities.Author;
import com.slm.springlibrarymanagement.model.entities.Book;
import com.slm.springlibrarymanagement.model.entities.Client;
import com.slm.springlibrarymanagement.service.dmo.DataLoaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataLoaderServiceImpl<T> implements DataLoaderService<T> {
    private final DataSource dataSource;
    private final AuthorRowMapper authorRowMapper;
    private final BookRowMapper bookRowMapper;
    private final ClientRowMapper clientRowMapper;

    @Autowired
    public DataLoaderServiceImpl(DataSource dataSource, AuthorRowMapper authorRowMapper, BookRowMapper bookRowMapper, ClientRowMapper clientRowMapper) {
        this.dataSource = dataSource;
        this.authorRowMapper = authorRowMapper;
        this.bookRowMapper = bookRowMapper;
        this.clientRowMapper = clientRowMapper;
    }

    @Override
    public List<T> loadData(String sql, T params) {
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            switch (params.getClass().getSimpleName()) {
                case "Author": {
                    List<Author> authorList = new ArrayList<>();
                    while (resultSet.next()) {
                        Author author = authorRowMapper.mapRow(resultSet, resultSet.getRow());
                        authorList.add(author);

                    }
                    return authorList.stream().map(author -> (T) author).collect(Collectors.toList());
                }
                case "Book": {
                    List<Book> bookList = new ArrayList<>();
                    while (resultSet.next()) {
                        Book book = bookRowMapper.mapRow(resultSet, resultSet.getRow());
                        bookList.add(book);
                    }

                    return bookList.stream().map(book -> (T) book).collect(Collectors.toList());
                }
                case "Client": {
                    List<Client> clientList = new ArrayList<>();
                    while (resultSet.next()) {
                        Client client = clientRowMapper.mapRow(resultSet, resultSet.getRow());
                        clientList.add(client);
                    }
                    return clientList.stream().map(client -> (T) client).collect(Collectors.toList());
                }
                default:
                    return Collections.emptyList();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
