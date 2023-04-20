package com.slm.springlibrarymanagement.controller.impl;

import com.slm.springlibrarymanagement.controller.AuthorController;
import com.slm.springlibrarymanagement.exceptions.InvalidIdException;
import com.slm.springlibrarymanagement.exceptions.NoEntriesFoundException;
import com.slm.springlibrarymanagement.exceptions.author.AuthorNotFoundException;
import com.slm.springlibrarymanagement.model.dto.AuthorDto;
import com.slm.springlibrarymanagement.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
public class AuthorControllerImpl implements AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorControllerImpl(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/authors")
    public ResponseEntity<List<AuthorDto>> getAllAuthors() {
        try {
            authorService.loadAuthorData();
            return ResponseEntity.ok(authorService.findAllAuthors());
        } catch (NoEntriesFoundException | InvalidIdException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable
                                                   String id) {

        try {
            AuthorDto authorDto = authorService.findAuthorById(id);
            return ResponseEntity.ok(authorDto);
        } catch (InvalidIdException | AuthorNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
