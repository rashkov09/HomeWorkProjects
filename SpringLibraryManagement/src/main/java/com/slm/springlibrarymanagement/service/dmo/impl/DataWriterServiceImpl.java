package com.slm.springlibrarymanagement.service.dmo.impl;

import com.slm.springlibrarymanagement.accessor.AuthorFileAccessor;
import com.slm.springlibrarymanagement.accessor.BookFileAccessor;
import com.slm.springlibrarymanagement.accessor.ClientFileAccessor;
import com.slm.springlibrarymanagement.exceptions.BackUpFailedException;
import com.slm.springlibrarymanagement.model.entities.Author;
import com.slm.springlibrarymanagement.model.entities.Book;
import com.slm.springlibrarymanagement.model.entities.Client;
import com.slm.springlibrarymanagement.service.dmo.DataWriterService;
import com.slm.springlibrarymanagement.util.CustomDateFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.List;

@Service
public class DataWriterServiceImpl<T> implements DataWriterService<T> {
    public final DataSource dataSource;
    public final AuthorFileAccessor authorFileAccessor;
    public final BookFileAccessor bookFileAccessor;
    private final CustomDateFormatter formatter;
    public final ClientFileAccessor clientFileAccessor;

    @Autowired
    public DataWriterServiceImpl(DataSource dataSource, AuthorFileAccessor authorFileAccessor, BookFileAccessor bookFileAccessor, CustomDateFormatter formatter, ClientFileAccessor clientFileAccessor) {
        this.dataSource = dataSource;
        this.authorFileAccessor = authorFileAccessor;
        this.bookFileAccessor = bookFileAccessor;
        this.formatter = formatter;
        this.clientFileAccessor = clientFileAccessor;
    }

    @Override
    public Long save(String sql, T param) throws IllegalArgumentException {
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            switch (param) {
                case Author author: {
                    preparedStatement.setString(1, author.getName());
                    preparedStatement.executeUpdate();
                    ResultSet rs = preparedStatement.getGeneratedKeys();

                    if (rs.next()) {
                        return rs.getLong(1);
                    }
                    return 0L;
                }
                case Book book: {
                    preparedStatement.setString(1, book.getName());
                    preparedStatement.setObject(2, book.getAuthor().getId());
                    preparedStatement.setDate(3, Date.valueOf(book.getIssueDate()));
                    preparedStatement.setInt(4, book.getNumberOfCopies());
                    preparedStatement.executeUpdate();
                    ResultSet rs = preparedStatement.getGeneratedKeys();
                    if (rs.next()) {
                        return rs.getLong(1);
                    }
                    return 0L;
                }
                case Client client: {
                    preparedStatement.setString(1, client.getFirstName());
                    preparedStatement.setString(2, client.getLastName());
                    preparedStatement.setString(3, client.getAddress());
                    preparedStatement.setString(4, client.getPhoneNumber());
                    preparedStatement.executeUpdate();
                    ResultSet rs = preparedStatement.getGeneratedKeys();
                    if (rs.next()) {
                        return rs.getLong(1);
                    }
                    return 0L;
                }
                default:
                    throw new IllegalArgumentException();

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int saveAll(String sql, List<T> paramList, T object) throws SQLException {
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sql)) {
            switch (object.getClass().getSimpleName()) {
                case "Author": {
                    paramList.stream().map(o -> (Author) o).forEach(author -> {
                        try {
                            preparedStatement.setString(1, author.getName());
                            preparedStatement.addBatch();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    int[] result = preparedStatement.executeBatch();
                    return result.length;
                }
                case "Book": {
                    paramList.stream().map(o -> (Book) o).forEach(book -> {
                        try {
                            preparedStatement.setString(1, book.getName());
                            preparedStatement.setObject(2, book.getAuthor().getId());
                            preparedStatement.setDate(3, Date.valueOf(book.getIssueDate()));
                            preparedStatement.setInt(4, book.getNumberOfCopies());
                            preparedStatement.addBatch();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    int[] result = preparedStatement.executeBatch();
                    return result.length;
                }
                case "Client": {
                    paramList.stream().map(o -> (Client) o).forEach(client -> {
                        try {
                            preparedStatement.setString(1, client.getFirstName());
                            preparedStatement.setString(2, client.getLastName());
                            preparedStatement.setString(3, client.getAddress());
                            preparedStatement.setString(4, client.getPhoneNumber());
                            preparedStatement.addBatch();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    int[] result = preparedStatement.executeBatch();
                    return result.length;
                }
                default:
                    throw new IllegalArgumentException();

            }
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean writeDataToFile(List<T> data, T param) throws BackUpFailedException {
        switch (param.getClass().getSimpleName()) {
            case "Author": {
                StringBuilder builder = new StringBuilder();
                data.stream().map(o -> (Author) o).forEach(author ->
                        builder
                                .append(String.format("%d. %s", author.getId(), author.getName()))
                                .append("\n"));
                try {
                    authorFileAccessor.writeLine(builder.toString().trim());
                    return true;
                } catch (IOException e) {
                    throw new BackUpFailedException();
                }
            }
            case "Book": {
                StringBuilder builder = new StringBuilder();
                data.stream().map(o -> (Book) o).forEach(book -> builder
                        .append(String.format("%d.%s_%s_%s",
                                book.getId(), book.getName(),
                                book.getAuthor().getName(),
                                book.getIssueDate().format(formatter.getFormatter())))
                        .append("\n"));

                try {
                    bookFileAccessor.writeLine(builder.toString().trim());
                    return true;
                } catch (IOException e) {
                    throw new BackUpFailedException();
                }
            }
            case "Client": {
                StringBuilder builder = new StringBuilder();
                data.stream().map(o -> (Client) o).forEach(client -> builder
                        .append(String.format("%d. %s %s", client.getId(), client.getFirstName(), client.getLastName()))
                        .append("\n"));
                try {
                    clientFileAccessor.writeLine(builder.toString().trim());
                    return true;
                } catch (IOException e) {
                    throw new BackUpFailedException();
                }
            }
            default:
                return false;
        }
    }
}
