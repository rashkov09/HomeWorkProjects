package com.slm.springlibrarymanagement.controller;

import com.slm.springlibrarymanagement.model.dto.AuthorDto;
import com.slm.springlibrarymanagement.model.dto.BookDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface BookController {

    @GetMapping("/books/{id}")
    ResponseEntity<BookDto> getBookById(@PathVariable
                                            String id);
    @GetMapping("/books")
    ResponseEntity<List<BookDto>> getAllBooks();

}
