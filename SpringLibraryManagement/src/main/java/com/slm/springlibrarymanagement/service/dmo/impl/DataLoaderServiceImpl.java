package com.slm.springlibrarymanagement.service.dmo.impl;

import com.slm.springlibrarymanagement.accessor.AuthorFileAccessor;
import com.slm.springlibrarymanagement.accessor.BookFileAccessor;
import com.slm.springlibrarymanagement.accessor.ClientFileAccessor;
import com.slm.springlibrarymanagement.mappers.AuthorRowMapper;
import com.slm.springlibrarymanagement.mappers.BookRowMapper;
import com.slm.springlibrarymanagement.mappers.ClientRowMapper;
import com.slm.springlibrarymanagement.model.entities.Author;
import com.slm.springlibrarymanagement.model.entities.Book;
import com.slm.springlibrarymanagement.model.entities.Client;
import com.slm.springlibrarymanagement.service.dmo.DataLoaderService;
import com.slm.springlibrarymanagement.util.CustomDateFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class DataLoaderServiceImpl<T> implements DataLoaderService<T> {
    private final DataSource dataSource;
    private final CustomDateFormatter formatter;
    private final AuthorRowMapper authorRowMapper;
    private final BookRowMapper bookRowMapper;
    private final ClientRowMapper clientRowMapper;

    private final AuthorFileAccessor authorFileAccessor;
    private final BookFileAccessor bookFileAccessor;
    private final ClientFileAccessor clientFileAccessor;

    @Autowired
    public DataLoaderServiceImpl(DataSource dataSource, CustomDateFormatter formatter, AuthorRowMapper authorRowMapper, BookRowMapper bookRowMapper, ClientRowMapper clientRowMapper, AuthorFileAccessor authorFileAccessor, BookFileAccessor bookFileAccessor, ClientFileAccessor clientFileAccessor) {
        this.dataSource = dataSource;
        this.formatter = formatter;
        this.authorRowMapper = authorRowMapper;
        this.bookRowMapper = bookRowMapper;
        this.clientRowMapper = clientRowMapper;
        this.authorFileAccessor = authorFileAccessor;
        this.bookFileAccessor = bookFileAccessor;
        this.clientFileAccessor = clientFileAccessor;
    }

    @Override
    public List<T> loadDataFromDb(String sql, T params) {
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

    @Override
    public List<T> loadDataFromFile(T object) {
        switch (object.getClass().getSimpleName()) {
            case "Author": {
                List<Author> authorList = new ArrayList<>();
                authorFileAccessor.readAllLines().forEach(line -> {
                    Author author = new Author();
                    if (line.contains(".")) {
                        String[] splitData = line.split("\\.\\s", 2);
                        author.setId(Long.parseLong(splitData[0]));
                        author.setName(splitData[1]);
                    }
                    authorList.add(author);
                });
                return authorList.stream().map(author -> (T) author).collect(Collectors.toList());
            }
            case "Book": {
                List<Book> bookList = new ArrayList<>();
                bookFileAccessor.readAllLines().forEach(line -> {
                    Book book = new Book();
                    setBookData(line, book);
                    bookList.add(book);
                });
                return bookList.stream().map(book -> (T) book).collect(Collectors.toList());
            }
            case "Client": {
                List<Client> clientList = new ArrayList<>();
                Random randomPhone = new Random();
                clientFileAccessor.readAllLines().forEach(line -> {
                    Client client = new Client();
                        String[] splitData = line.split("\\.", 2);
                        client.setId(Long.parseLong(splitData[0]));
                        String[] names = splitData[1].split("\\s");
                        client.setFirstName(names[0]);
                        client.setLastName(names[1]);
                        client.setPhoneNumber("+" + randomPhone.nextInt(111111111, 999999999));
                    clientList.add(client);
                });
                return clientList.stream().map(client -> (T) client).collect(Collectors.toList());
            }
            default:
                return Collections.emptyList();
        }

    }

    private void setBookData(String line, Book book) {
        Author author = new Author();
        String[] splitBookData = line.split("_");
        String[] splitId = splitBookData[0].split("\\.", 2);
        book.setId(Long.parseLong(splitId[0]));
        book.setName(splitId[1]);
        author.setName(splitBookData[1]);
        book.setAuthor(author);
        book.setIssueDate(LocalDate.parse(splitBookData[2], formatter.getFormatter()));

        book.setNumberOfCopies(1);
    }
}


