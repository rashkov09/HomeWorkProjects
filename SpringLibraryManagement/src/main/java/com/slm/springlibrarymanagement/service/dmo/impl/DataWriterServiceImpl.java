package com.slm.springlibrarymanagement.service.dmo.impl;

import com.slm.springlibrarymanagement.model.entities.Author;
import com.slm.springlibrarymanagement.model.entities.Book;
import com.slm.springlibrarymanagement.model.entities.Client;
import com.slm.springlibrarymanagement.service.dmo.DataWriterService;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Service
public class DataWriterServiceImpl<T> implements DataWriterService<T> {
    public final DataSource dataSource;

    @Autowired
    public DataWriterServiceImpl(DataSource dataSource) {
        this.dataSource = dataSource;


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
    public int saveAll(String sql, List<T> paramList) {
        return 0;
    }
}
