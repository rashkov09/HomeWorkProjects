package com.slm.springlibrarymanagement.repository;

import com.slm.springlibrarymanagement.exceptions.BackUpFailedException;
import com.slm.springlibrarymanagement.exceptions.InvalidIdException;
import com.slm.springlibrarymanagement.model.entities.Author;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;


public interface AuthorRepository {

    void loadAuthors() throws SQLException, InvalidIdException;

    List<Author> findAll();

    Author findByName(String authorName) throws NoSuchElementException;

    Author findById(Long id) throws NoSuchElementException;

    boolean addAuthor(Author author);

    boolean writeDataToFile() throws BackUpFailedException;
}
