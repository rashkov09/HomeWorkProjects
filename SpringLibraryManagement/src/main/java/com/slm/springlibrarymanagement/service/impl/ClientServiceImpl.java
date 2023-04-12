package com.slm.springlibrarymanagement.service.impl;

import com.slm.springlibrarymanagement.accessor.ClientFileAccessor;
import com.slm.springlibrarymanagement.exceptions.BackUpFailedException;
import com.slm.springlibrarymanagement.exceptions.NoEntriesFoundException;
import com.slm.springlibrarymanagement.exceptions.client.*;
import com.slm.springlibrarymanagement.model.entities.Client;
import com.slm.springlibrarymanagement.repository.ClientRepository;
import com.slm.springlibrarymanagement.service.ClientService;
import com.slm.springlibrarymanagement.util.InputValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class ClientServiceImpl implements ClientService {
    private final static String CLIENT_VIEW_TEMPLATE = "%d. %s %s";
    private final ClientRepository clientRepository;
    private final InputValidator inputValidator;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, InputValidator inputValidator) {
        this.clientRepository = clientRepository;
        this.inputValidator = inputValidator;
    }

    @Override
    public String findAllClients() throws NoEntriesFoundException {
        StringBuilder builder = new StringBuilder();
        clientRepository.findAll().forEach(client -> builder.append(String.format(CLIENT_VIEW_TEMPLATE,
                client.getId(),
                client.getFirstName(),
                client.getLastName())).append("\n"));
        if (builder.toString().isEmpty() || builder.toString().isBlank()) {
            throw new NoEntriesFoundException();
        }

        return builder.toString();
    }


    @Override
    public void backupToFile() throws BackUpFailedException {
        clientRepository.backupToFile();
    }

    @Override
    public String insertClient(String firstName, String lastName, String address, String phoneNumber) throws InvalidClientFirstNameException, InvalidClientLastNameException, InvalidClientPhoneException, ClientAlreadyExistsException {
        Client client = new Client();
        if (inputValidator.isNotValidFirstLastName(firstName)) {
            throw new InvalidClientFirstNameException();
        }
        if (inputValidator.isNotValidFirstLastName(lastName)) {
            throw new InvalidClientLastNameException();
        }
        client.setFirstName(firstName);
        client.setLastName(lastName);
        if (!address.isBlank() && !address.isEmpty()) {
            client.setAddress(address);
        }
        if (inputValidator.isNotValidPhoneNumber(phoneNumber)) {
            throw new InvalidClientPhoneException();
        }
        try {
            findClientByPhoneNumber(phoneNumber);
            throw new ClientAlreadyExistsException();
        } catch (ClientNotFoundException e) {
            client.setPhoneNumber(phoneNumber);

            if(clientRepository.addClient(client)){
                return String.format("Client %s added successfully!", client.fullName());
            };
        }
        return "Client was not added successfully! Please, try again!";
    }

    @Override
    public Client findClientByPhoneNumber(String phoneNumber) throws ClientNotFoundException, InvalidClientPhoneException {
        if (inputValidator.isNotValidPhoneNumber(phoneNumber)) {
            throw new InvalidClientPhoneException();
        }
        Client client = clientRepository.findByPhoneNumber(phoneNumber);
        if (client == null) {
            throw new ClientNotFoundException();
        }
        return client;
    }

    @Override
    public String findClientsByFirstName(String firstName) throws InvalidClientFirstNameException, ClientNotFoundException {
        StringBuilder builder = new StringBuilder();
        if (inputValidator.isNotValidFirstLastName(firstName)) {
            throw new InvalidClientFirstNameException();
        }
        List<Client> clients = clientRepository.findByFirstName(firstName);
        if (clients.isEmpty()) {
            throw new ClientNotFoundException();
        }
        clients.forEach(client -> builder.append(client.toString()).append("\n"));
        return builder.toString();
    }

    @Override
    public String findClientsByLastName(String lastName) throws InvalidClientLastNameException, ClientNotFoundException {
        StringBuilder builder = new StringBuilder();
        if (inputValidator.isNotValidFirstLastName(lastName)) {
            throw new InvalidClientLastNameException();
        }
        List<Client> clients = clientRepository.findByLastName(lastName);
        if (clients.isEmpty()) {
            throw new ClientNotFoundException();
        }
        clients.forEach(client -> builder.append(client.toString()).append("\n"));
        return builder.toString();
    }

    @Override
    public Client findClientByFullName(String fullName) throws ClientNotFoundException {
        try {
            return clientRepository.findClientByFirstNameAndLastName(fullName);
        }catch (NoSuchElementException e){
            throw new ClientNotFoundException();
        }
    }

    @Override
    public Client findClientById(Long clientId) throws ClientNotFoundException {
        try {
            return clientRepository.findById(clientId);
        } catch (NoSuchElementException e) {
            throw new ClientNotFoundException();
        }
    }

    @Override
    public void loadClientData() throws SQLException {
        clientRepository.loadClients();
    }
}
