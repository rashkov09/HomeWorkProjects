package com.slm.springlibrarymanagement.controller;

import com.slm.springlibrarymanagement.controller.request.UserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/users")
public interface UserController {

    @PostMapping("/register")
     ResponseEntity<Void> registerUser(@RequestBody @Valid UserRequest userRequest);
}
