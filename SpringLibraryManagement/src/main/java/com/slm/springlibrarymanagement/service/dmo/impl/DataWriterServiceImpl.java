package com.slm.springlibrarymanagement.service.dmo.impl;

import com.slm.springlibrarymanagement.accessor.AuthorFileAccessor;
import com.slm.springlibrarymanagement.accessor.BookFileAccessor;
import com.slm.springlibrarymanagement.accessor.ClientFileAccessor;
import com.slm.springlibrarymanagement.accessor.OrderFileAccessor;
import com.slm.springlibrarymanagement.constants.ClassesEnum;
import com.slm.springlibrarymanagement.exceptions.BackUpFailedException;
import com.slm.springlibrarymanagement.model.entities.Author;
import com.slm.springlibrarymanagement.model.entities.Book;
import com.slm.springlibrarymanagement.model.entities.Client;
import com.slm.springlibrarymanagement.model.entities.Order;
import com.slm.springlibrarymanagement.service.dmo.DataWriterService;
import com.slm.springlibrarymanagement.util.CustomDateFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

@Service
public class DataWriterServiceImpl<T> implements DataWriterService<T> {
    public final DataSource dataSource;
    public final AuthorFileAccessor authorFileAccessor;
    public final BookFileAccessor bookFileAccessor;
    public final ClientFileAccessor clientFileAccessor;
    public final OrderFileAccessor orderFileAccessor;
    private final CustomDateFormatter formatter;

    private static final String ORDER_TO_FILE_TEMPLATE = "%d.%s_%s_%s_%s_%d";
    private static final String CLIENT_TO_FILE_TEMPLATE = "%d. %s %s";
    private static final String BOOK_TO_FILE_TEMPLATE = "%d.%s_%s_%s";

    @Autowired
    public DataWriterServiceImpl(DataSource dataSource, AuthorFileAccessor authorFileAccessor, BookFileAccessor bookFileAccessor, CustomDateFormatter formatter, ClientFileAccessor clientFileAccessor, OrderFileAccessor orderFileAccessor) {
        this.dataSource = dataSource;
        this.authorFileAccessor = authorFileAccessor;
        this.bookFileAccessor = bookFileAccessor;
        this.formatter = formatter;
        this.clientFileAccessor = clientFileAccessor;
        this.orderFileAccessor = orderFileAccessor;
    }

    @Override
    public Long save(String sql, T param) throws IllegalArgumentException {
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            switch (param) {
                case Author author -> {
                    preparedStatement.setString(1, author.getName());
                    preparedStatement.executeUpdate();
                    ResultSet rs = preparedStatement.getGeneratedKeys();

                    if (rs.next()) {
                        return rs.getLong(1);
                    }
                    return 0L;
                }
                case Book book -> {
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
                case Client client -> {
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
                case Order order -> {
                    preparedStatement.setObject(1, order.getClient().getId());
                    preparedStatement.setObject(2, order.getBook().getId());
                    preparedStatement.setDate(3, Date.valueOf(order.getIssueDate()));
                    preparedStatement.setDate(4, Date.valueOf(order.getDueDate()));
                    preparedStatement.setInt(5, order.getBookCount());
                    preparedStatement.executeUpdate();
                    ResultSet rs = preparedStatement.getGeneratedKeys();
                    if (rs.next()) {
                        return rs.getLong(1);
                    }
                    return 0L;
                }
                default -> throw new IllegalArgumentException();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int saveAll(String sql, List<T> paramList, ClassesEnum classType) {
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sql)) {
            switch (classType) {
                case Author -> {
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
                case Book -> {
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
                case Client -> {
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
                case Order -> {
                    paramList.stream().map(o -> (Order) o).forEach(order -> {
                        try {
                            preparedStatement.setObject(1, order.getClient().getId());
                            preparedStatement.setObject(2, order.getBook().getId());
                            preparedStatement.setDate(3, Date.valueOf(order.getIssueDate()));
                            preparedStatement.setDate(4, Date.valueOf(order.getDueDate()));
                            preparedStatement.setInt(5, order.getBookCount());
                            preparedStatement.addBatch();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    int[] result = preparedStatement.executeBatch();
                    return result.length;
                }
                default -> throw new IllegalArgumentException();
            }
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean writeDataToFile(List<T> data, ClassesEnum classType) throws BackUpFailedException {
        switch (classType) {
            case Author -> {
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
            case Book -> {
                StringBuilder builder = new StringBuilder();
                data.stream().map(o -> (Book) o).forEach(book -> builder
                        .append(String.format(BOOK_TO_FILE_TEMPLATE,
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
            case Client -> {
                StringBuilder builder = new StringBuilder();
                data.stream().map(o -> (Client) o).forEach(client -> builder
                        .append(String.format(CLIENT_TO_FILE_TEMPLATE, client.getId(), client.getFirstName(), client.getLastName()))
                        .append("\n"));
                try {
                    clientFileAccessor.writeLine(builder.toString().trim());
                    return true;
                } catch (IOException e) {
                    throw new BackUpFailedException();
                }
            }
            case Order -> {
                StringBuilder builder = new StringBuilder();
                data.stream().map(o -> (Order) o).forEach(order -> builder
                        .append(String.format(ORDER_TO_FILE_TEMPLATE, order.getId(), order.getClient().fullName(), order.getBook().getName(), order.getIssueDate().format(formatter.getFormatter()), order.getDueDate().format(formatter.getFormatter()), order.getBookCount()))
                        .append("\n"));
                try {
                    orderFileAccessor.writeLine(builder.toString().trim());
                    return true;
                } catch (IOException e) {
                    throw new BackUpFailedException();
                }
            }
            default -> {
                return false;
            }
        }
    }

    @Override
    public boolean update(String sql, T param, ClassesEnum classType) throws SQLException {
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sql)) {
            switch (classType) {
                case Author -> {
                    // implement author update
                    return true;
                }
                case Book -> {
                    Book book = (Book) param;
                    preparedStatement.setInt(1, book.getNumberOfCopies());
                    preparedStatement.setLong(2, book.getId());
                    int result = preparedStatement.executeUpdate();
                    return result != 0;
                }
                case Client -> {
                    int result = preparedStatement.executeUpdate();
                    return result != 0;
                }
                case Order -> {
                    Order order = (Order) param;
                    preparedStatement.setDate(1, Date.valueOf(order.getDueDate()));
                    preparedStatement.setDate(2, Date.valueOf(LocalDate.now()));
                    preparedStatement.setLong(3, order.getId());
                    int result = preparedStatement.executeUpdate();
                    return result != 0;
                }
            }
        }
        return false;
    }
}
