package com.slm.springlibrarymanagement.controller.impl;

import com.slm.springlibrarymanagement.controller.AuthorController;
import com.slm.springlibrarymanagement.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorControllerImpl implements AuthorController {
    private final AuthorService authorService;

    public AuthorControllerImpl(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/")
    public ResponseEntity<String> getIndex(){
        return ResponseEntity.ok("Welcome to the index page");
    }
}
