package com.slm.springlibrarymanagement.controller.impl;

import com.slm.springlibrarymanagement.controller.AuthorController;
import com.slm.springlibrarymanagement.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorControllerImpl implements AuthorController {
    private final AuthorService authorService;

    public AuthorControllerImpl(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    public ResponseEntity<String> add() {
       // authorService.loadAllAuthors();
        return ResponseEntity.ok("Added");
    }

    @Override
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello");
    }
}
