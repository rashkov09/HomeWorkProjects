package com.slm.springlibrarymanagement.service.dmo;

        import com.slm.springlibrarymanagement.exceptions.InvalidIdException;
        import com.slm.springlibrarymanagement.exceptions.author.AuthorNotFoundException;
        import com.slm.springlibrarymanagement.mappers.AuthorRowMapper;
        import com.slm.springlibrarymanagement.mappers.BookRowMapper;
        import com.slm.springlibrarymanagement.mappers.ClientRowMapper;
        import com.slm.springlibrarymanagement.model.entities.Author;
        import com.slm.springlibrarymanagement.model.entities.Book;
        import com.slm.springlibrarymanagement.model.entities.Client;
        import com.slm.springlibrarymanagement.service.AuthorService;
        import org.springframework.stereotype.Component;

        import javax.sql.DataSource;
        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.util.ArrayList;
        import java.util.Collections;
        import java.util.List;

@Component
public class DataLoaderService {
    private final DataSource dataSource;
    private final AuthorService authorService;
    private final AuthorRowMapper authorRowMapper;
    private final BookRowMapper bookRowMapper;
    private final ClientRowMapper clientRowMapper;

    public DataLoaderService(DataSource dataSource, AuthorService authorService, AuthorRowMapper authorRowMapper, BookRowMapper bookRowMapper, ClientRowMapper clientRowMapper) {
        this.dataSource = dataSource;
        this.authorService = authorService;
        this.authorRowMapper = authorRowMapper;
        this.bookRowMapper = bookRowMapper;
        this.clientRowMapper = clientRowMapper;
    }


    public List<Object> loadData(String sql, Object params) throws SQLException, AuthorNotFoundException, InvalidIdException {
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            switch (params.getClass().getSimpleName()) {
                case "Author": {
                    List<Author> authorList = new ArrayList<>();
                    while (resultSet.next()) {
                        Author author = authorRowMapper.mapRow(resultSet, resultSet.getRow());
                        authorList.add(author);
                    }
                    return Collections.singletonList(authorList);
                }
                case "Book": {
                    List<Book> bookList = new ArrayList<>();
                    while (resultSet.next()) {
                        while (resultSet.next()) {
                            Book book = bookRowMapper.mapRow(resultSet, resultSet.getRow());
                            Author author = authorService.findAuthorById(String.valueOf(resultSet.getLong(5)));
                            book.setAuthor(author);
                            bookList.add(book);
                        }
                    }
                    return Collections.singletonList(bookList);
                }
                case "Client": {
                    List<Client> clientList = new ArrayList<>();
                    while (resultSet.next()) {
                        Client client = clientRowMapper.mapRow(resultSet, resultSet.getRow());
                        clientList.add(client);
                    }
                    return Collections.singletonList(clientList);
                }
                default: return Collections.emptyList();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
