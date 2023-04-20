package com.slm.springlibrarymanagement.controller;

import com.slm.springlibrarymanagement.model.dto.AuthorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


public interface AuthorController {

    @GetMapping("/authors/{id}")
    ResponseEntity<AuthorDto> getAuthorById(@PathVariable
                                            String id);
    @GetMapping("/authors")
    ResponseEntity<List<AuthorDto>> getAllAuthor();

}
