package com.slm.springlibrarymanagement.repository.impl;

import com.slm.springlibrarymanagement.model.entities.Author;
import com.slm.springlibrarymanagement.repository.AuthorRepository;
import com.slm.springlibrarymanagement.service.dmo.DataLoaderService;
import com.slm.springlibrarymanagement.service.dmo.DataWriterService;
import com.slm.springlibrarymanagement.service.dmo.impl.DataLoaderServiceImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {
    private static List<Author> authorList;
    private final DataLoaderService<Author> dataLoaderService;
    private final DataWriterService<Author> dataWriterService;

    public AuthorRepositoryImpl(DataLoaderServiceImpl<Author> dataLoaderService, DataWriterService<Author> dataWriterService) {
        this.dataLoaderService = dataLoaderService;
        this.dataWriterService = dataWriterService;
        authorList = new ArrayList<>();
    }

    @Override
    public void loadAuthors() {
        String sql = "SELECT * FROM slm.authors";
        authorList = dataLoaderService.loadData(sql, new Author());

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

    @Override
    public void addAll(List<Author> authorsList) {
    }

    @Override
    public boolean addAuthor(Author author) {
        String sql = "INSERT INTO slm.authors (name) VALUES(?)";
        Long id = dataWriterService.save(sql,author);
        if (id != 0L) {
            author.setId(id);
            authorList.add(author);
            return true;
        }
        return false;
    }
}
