package com.slm.springlibrarymanagement.controller.impl;

import com.slm.springlibrarymanagement.controller.ClientController;
import com.slm.springlibrarymanagement.mappers.ClientMapper;
import com.slm.springlibrarymanagement.model.dto.ClientDto;
import com.slm.springlibrarymanagement.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class ClientControllerImpl implements ClientController {
    public final ClientService clientService;

    public ClientControllerImpl(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public ResponseEntity<ClientDto> getClientById(String id) {
        ClientDto clientDto = clientService.findClientById(id);
        return ResponseEntity.ok(clientDto);
    }

    @Override
    public ResponseEntity<List<ClientDto>> getAllClients() {
        return ResponseEntity.ok(clientService.findAllClients());
    }
}
