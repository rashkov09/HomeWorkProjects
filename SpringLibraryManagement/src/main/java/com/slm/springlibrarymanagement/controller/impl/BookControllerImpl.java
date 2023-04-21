package com.slm.springlibrarymanagement.controller.impl;

import com.slm.springlibrarymanagement.controller.BookController;
import com.slm.springlibrarymanagement.controller.request.BookRequest;
import com.slm.springlibrarymanagement.model.dto.BookDto;
import com.slm.springlibrarymanagement.model.entities.Book;
import com.slm.springlibrarymanagement.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class BookControllerImpl implements BookController {
    private final BookService bookService;

    public BookControllerImpl(BookService bookService) {
        this.bookService = bookService;
    }


    @Override
    public ResponseEntity<BookDto> getBookById(String id) {
        BookDto bookDto = bookService.findBookById(Long.parseLong(id));
        return ResponseEntity.ok(bookDto);
    }

    @Override
    public ResponseEntity<List<BookDto>> getAllBooks() {
        return ResponseEntity.ok(bookService.findAllBooks());
    }

    @Override
    public ResponseEntity<Void> createBook(BookRequest bookRequest) {
        Book book = bookService.insertBook(bookRequest);

        URI location = UriComponentsBuilder.fromUriString("/books/{id}")
                .buildAndExpand(book.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
