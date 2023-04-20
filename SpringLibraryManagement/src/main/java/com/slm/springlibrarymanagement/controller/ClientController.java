package com.slm.springlibrarymanagement.controller;

import com.slm.springlibrarymanagement.model.dto.ClientDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ClientController {
    @GetMapping("/clients/{id}")
    ResponseEntity<ClientDto> getClientById(@PathVariable
                                            String id);
    @GetMapping("/clients")
    ResponseEntity<List<ClientDto>> getAllClients();
}
