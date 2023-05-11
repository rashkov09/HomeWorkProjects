package com.slm.springlibrarymanagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/authors")
public interface AuthorController {

    @GetMapping()
    public ResponseEntity<String> add();

    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    ResponseEntity<String> sayHello();
}
