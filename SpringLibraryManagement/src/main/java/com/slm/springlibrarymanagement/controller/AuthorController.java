package com.slm.springlibrarymanagement.controller;

import com.slm.springlibrarymanagement.controller.request.AuthorRequest;
import com.slm.springlibrarymanagement.model.dto.AuthorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;


public interface AuthorController {

    @GetMapping("/authors/{id}")
    ResponseEntity<AuthorDto> getAuthorById(@PathVariable
                                            String id);
    @GetMapping("/authors")
    ResponseEntity<List<AuthorDto>> getAllAuthor();

    @PostMapping("/authors")
    ResponseEntity<Void> createItem(@RequestBody @Valid AuthorRequest authorRequest);

}
