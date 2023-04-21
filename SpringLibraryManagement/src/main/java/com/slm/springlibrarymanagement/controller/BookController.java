package com.slm.springlibrarymanagement.controller;

import com.slm.springlibrarymanagement.controller.request.BookRequest;
import com.slm.springlibrarymanagement.model.dto.BookDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

public interface BookController {

    @GetMapping("/books/{id}")
    ResponseEntity<BookDto> getBookById(@PathVariable
                                        String id);

    @GetMapping("/books")
    ResponseEntity<List<BookDto>> getAllBooks();

    @PostMapping("/books")
    ResponseEntity<Void> createBook(@RequestBody @Valid BookRequest bookRequest);

}
