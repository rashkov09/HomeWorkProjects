package com.slm.springlibrarymanagement.service;

import com.slm.springlibrarymanagement.controller.request.ClientRequest;
import com.slm.springlibrarymanagement.exceptions.BackUpFailedException;
import com.slm.springlibrarymanagement.exceptions.client.ClientNotFoundException;
import com.slm.springlibrarymanagement.exceptions.client.InvalidClientFirstNameException;
import com.slm.springlibrarymanagement.exceptions.client.InvalidClientLastNameException;
import com.slm.springlibrarymanagement.exceptions.client.InvalidClientPhoneException;
import com.slm.springlibrarymanagement.model.dto.ClientDto;
import com.slm.springlibrarymanagement.model.entities.Client;

import java.util.List;

public interface ClientService {
    List<ClientDto> findAllClients();

    void backupToFile() throws BackUpFailedException;

    Client insertClient(ClientRequest clientRequest);

    Client findClientByPhoneNumber(String phoneNumber) throws ClientNotFoundException, InvalidClientPhoneException;

    String findClientsByFirstName(String firstName) throws InvalidClientFirstNameException, ClientNotFoundException;

    String findClientsByLastName(String lastName) throws InvalidClientLastNameException, ClientNotFoundException;

    Client findClientByFullName(String fullName) throws ClientNotFoundException;

    ClientDto findClientById(String clientId);

}
