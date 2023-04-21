package com.slm.springlibrarymanagement.controller.impl;

import com.slm.springlibrarymanagement.controller.AuthorController;
import com.slm.springlibrarymanagement.controller.request.AuthorRequest;
import com.slm.springlibrarymanagement.model.dto.AuthorDto;
import com.slm.springlibrarymanagement.model.entities.Author;
import com.slm.springlibrarymanagement.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class AuthorControllerImpl implements AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorControllerImpl(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable
                                                   String id) {
        AuthorDto authorDto = authorService.findAuthorById(id);
        return ResponseEntity.ok(authorDto);
    }

    @Override
    public ResponseEntity<List<AuthorDto>> getAllAuthor() {
        return ResponseEntity.ok(authorService.findAllAuthors());
    }

    @Override
    public ResponseEntity<Void> createItem(AuthorRequest authorRequest) {
        Author author = authorService.insertAuthor(authorRequest);

        URI location = UriComponentsBuilder.fromUriString("/authors/{id}")
                .buildAndExpand(author.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}


