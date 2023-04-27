package com.scalefocus.midterm.trippyapp.controller;

import com.scalefocus.midterm.trippyapp.controller.request.UserRequest;
import com.scalefocus.midterm.trippyapp.model.dto.UserDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/users")
public interface UserController {

    @GetMapping()
    ResponseEntity<List<UserDto>> getAllUsers();

    @GetMapping("/{id}")
    ResponseEntity<UserDto> getUserById(@PathVariable
                                        Long id);

    @GetMapping("/username/{username}")
    ResponseEntity<UserDto> getUserByUsername(@PathVariable
                                              String username);

    @GetMapping("/email/{email}")
    ResponseEntity<UserDto> getUserByEmail(@PathVariable
                                           String email);

    @PostMapping()
    ResponseEntity<Void> addUser(@RequestBody @Valid UserRequest userRequest);

    @PutMapping("/{id}")
    ResponseEntity<UserDto> updateUser(
            @RequestBody @Valid UserRequest userRequest, @PathVariable int id,
            @RequestParam(required = false) boolean returnOld);

}
