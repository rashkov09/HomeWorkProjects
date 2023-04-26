package com.scalefocus.midterm.trippyapp.controller;

import com.scalefocus.midterm.trippyapp.controller.request.UserRequest;
import com.scalefocus.midterm.trippyapp.model.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public interface UserController {

    @GetMapping("/users/{id}")
    ResponseEntity<UserDto> getUserById(@PathVariable
                                        String id);

    @PostMapping("/users")
    ResponseEntity<Void> addUser(@RequestBody @Valid UserRequest userRequest);

    @PutMapping("/users/{id}")
    ResponseEntity<UserDto> updateItem(
            @RequestBody @Valid UserRequest itemRequest, @PathVariable int id,
            @RequestParam(required = false) boolean returnOld);

}
