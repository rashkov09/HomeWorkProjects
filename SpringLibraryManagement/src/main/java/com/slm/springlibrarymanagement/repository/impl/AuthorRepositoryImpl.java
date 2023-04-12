package com.slm.springlibrarymanagement.repository.impl;

import com.slm.springlibrarymanagement.exceptions.BackUpFailedException;
import com.slm.springlibrarymanagement.model.entities.Author;
import com.slm.springlibrarymanagement.repository.AuthorRepository;
import com.slm.springlibrarymanagement.service.dmo.DataLoaderService;
import com.slm.springlibrarymanagement.service.dmo.DataWriterService;
import com.slm.springlibrarymanagement.service.dmo.impl.DataLoaderServiceImpl;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {
    private static List<Author> authorList;
    private final DataLoaderService<Author> dataLoaderService;
    private final DataWriterService<Author> dataWriterService;

    private final static String INSERT_AUTHOR_SQL = "INSERT INTO slm.authors (name) VALUES(?)";

    public AuthorRepositoryImpl(DataLoaderServiceImpl<Author> dataLoaderService, DataWriterService<Author> dataWriterService) {
        this.dataLoaderService = dataLoaderService;
        this.dataWriterService = dataWriterService;
        authorList = new ArrayList<>();
    }

    @Override
    public void loadAuthors() throws SQLException {
        String sql = "SELECT * FROM slm.authors";
        authorList = dataLoaderService.loadDataFromDb(sql, new Author());
        if (authorList.isEmpty()) {
            authorList = dataLoaderService.loadDataFromFile(new Author());
            addAll();
        }
    }

    @Override
    public List<Author> findAll() {
        return authorList;
    }

    @Override
    public Author findByName(String authorName) throws NoSuchElementException {
        return authorList.stream().filter(author -> author.getName().equals(authorName)).findFirst().orElseThrow();
    }

    @Override
    public Author findById(Long id) throws NoSuchElementException {
        return authorList.stream().filter(author -> author.getId().equals(id)).findFirst().orElseThrow();
    }


    private void addAll() throws SQLException {
        dataWriterService.saveAll(INSERT_AUTHOR_SQL, authorList, new Author());
    }

    @Override
    public boolean addAuthor(Author author) {
        Long id = dataWriterService.save(INSERT_AUTHOR_SQL, author);
        if (id != 0L) {
            author.setId(id);
            authorList.add(author);
            return true;
        }
        return false;
    }

    @Override
    public boolean writeDataToFile() throws BackUpFailedException {
        return dataWriterService.writeDataToFile(authorList, new Author());
    }
}
