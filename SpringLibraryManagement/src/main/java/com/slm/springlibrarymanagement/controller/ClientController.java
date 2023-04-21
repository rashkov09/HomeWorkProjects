package com.slm.springlibrarymanagement.controller;

import com.slm.springlibrarymanagement.controller.request.ClientRequest;
import com.slm.springlibrarymanagement.model.dto.ClientDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

public interface ClientController {
    @GetMapping("/clients/{id}")
    ResponseEntity<ClientDto> getClientById(@PathVariable
                                            String id);
    @GetMapping("/clients")
    ResponseEntity<List<ClientDto>> getAllClients();

    @PostMapping("/clients")
    ResponseEntity<Void> createClient(@RequestBody @Valid ClientRequest clientRequest);
}
