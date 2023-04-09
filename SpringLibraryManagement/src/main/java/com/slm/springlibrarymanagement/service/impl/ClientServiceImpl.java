package com.slm.springlibrarymanagement.service.impl;

import com.slm.springlibrarymanagement.accessor.ClientFileAccessor;
import com.slm.springlibrarymanagement.exceptions.BackUpFailedException;
import com.slm.springlibrarymanagement.exceptions.FileForEntityNotFound;
import com.slm.springlibrarymanagement.exceptions.NoEntriesFoundException;
import com.slm.springlibrarymanagement.exceptions.client.*;
import com.slm.springlibrarymanagement.model.entities.Client;
import com.slm.springlibrarymanagement.repository.ClientRepository;
import com.slm.springlibrarymanagement.service.ClientService;
import com.slm.springlibrarymanagement.util.InputValidator;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ClientServiceImpl implements ClientService {
    private final static String CLIENT_VIEW_TEMPLATE = "%d. %s %s";
    private final ClientRepository clientRepository;
    private final ClientFileAccessor clientFileAccessor;
    private final InputValidator inputValidator;

    public ClientServiceImpl(ClientRepository clientRepository, ClientFileAccessor clientFileAccessor, InputValidator inputValidator) {
        this.clientRepository = clientRepository;
        this.clientFileAccessor = clientFileAccessor;
        this.inputValidator = inputValidator;
    }

    @Override
    public String findAllClients() throws NoEntriesFoundException {
        StringBuilder builder = new StringBuilder();
        clientRepository.findAll().forEach(client -> builder.append(String.format(CLIENT_VIEW_TEMPLATE, client.getId(), client.getFirstName(), client.getLastName())).append("\n"));
        if (builder.toString().isEmpty() || builder.toString().isBlank()) {
            throw new NoEntriesFoundException();
        }

        return builder.toString();
    }

    @Override
    public void importClients() throws FileForEntityNotFound {
        try {
            findAllClients();
        } catch (NoEntriesFoundException e) {
            List<Client> clientList = new ArrayList<>();
            try {
                clientFileAccessor.readAllLines().forEach(line -> {
                    Client client = new Client();
                    if (line.contains(".")) {
                        String[] splitData = line.split("\\.\\s", 2);
                        String[] names = splitData[1].split("\\s");
                        client.setFirstName(names[0]);
                        client.setLastName(names[1]);
                        // TODO add other fields for client

                    } else {
                        String[] names = line.split("\\s");
                        client.setFirstName(names[0]);
                        client.setLastName(names[1]);
                    }
                    clientList.add(client);
                });

            } catch (Exception ex) {
                throw new FileForEntityNotFound();
            }
            clientRepository.saveAll(clientList);
        }
    }

    @Override
    public void backupToFile() throws BackUpFailedException {
        StringBuilder builder = new StringBuilder();
        clientRepository.findAll().forEach(client -> builder
                .append(String.format("%d.%s %s", client.getId(), client.getFirstName(), client.getLastName()))
                .append("\n"));

        try {
            clientFileAccessor.writeLine(builder.toString().trim());
        } catch (IOException e) {
            throw new BackUpFailedException();
        }
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

            clientRepository.save(client);
        }


        return String.format("Client %s added successfully!", client.fullName());
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
        Set<Client> clients = clientRepository.findByFirstName(firstName);
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
        Set<Client> clients = clientRepository.findByLastName(lastName);
        if (clients.isEmpty()) {
            throw new ClientNotFoundException();
        }
        clients.forEach(client -> builder.append(client.toString()).append("\n"));
        return builder.toString();
    }
}
