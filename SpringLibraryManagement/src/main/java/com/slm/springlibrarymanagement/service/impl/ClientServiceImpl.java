package com.slm.springlibrarymanagement.service.impl;

import com.slm.springlibrarymanagement.exceptions.BackUpFailedException;
import com.slm.springlibrarymanagement.exceptions.InvalidIdException;
import com.slm.springlibrarymanagement.exceptions.NoEntriesFoundException;
import com.slm.springlibrarymanagement.exceptions.client.*;
import com.slm.springlibrarymanagement.mappers.ClientMapper;
import com.slm.springlibrarymanagement.model.dto.ClientDto;
import com.slm.springlibrarymanagement.model.entities.Client;
import com.slm.springlibrarymanagement.repository.ClientRepository;
import com.slm.springlibrarymanagement.service.ClientService;
import com.slm.springlibrarymanagement.util.InputValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import static com.slm.springlibrarymanagement.constants.messages.ClientMessages.*;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final InputValidator inputValidator;

    private final ClientMapper clientMapper;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, InputValidator inputValidator, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.inputValidator = inputValidator;
        this.clientMapper = clientMapper;
    }

    @Override
    public List<ClientDto> findAllClients() {
        List<ClientDto> clientDtos = clientMapper.mapToDtoList(clientRepository.findAllClients());
        if (clientDtos.isEmpty()) {
            throw new NoEntriesFoundException();
        }
        return clientDtos;
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

            if (clientRepository.addClient(client)) {
                return String.format(CLIENT_ADDED_SUCCESSFULLY_MESSAGE, client.fullName());
            }

        }
        return CLIENT_ADDITION_FAILED_MESSAGE;
    }

    @Override
    public Client findClientByPhoneNumber(String phoneNumber) throws ClientNotFoundException, InvalidClientPhoneException {
        if (inputValidator.isNotValidPhoneNumber(phoneNumber)) {
            throw new InvalidClientPhoneException();
        }
        try {
            return clientRepository.findByPhoneNumber(phoneNumber);

        } catch (NoSuchElementException e) {
            throw new ClientNotFoundException();
        }

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
        } catch (NoSuchElementException e) {
            throw new ClientNotFoundException();
        }
    }

    @Override
    public ClientDto findClientById(String clientId) throws ClientNotFoundException {
        long id;
        try{
          id=  Long.parseLong(clientId);
        }catch (NumberFormatException e){
            throw new InvalidIdException();
        }
        try {
            return clientMapper.mapToDto(clientRepository.findById(id));
        } catch (NoSuchElementException e) {
            throw new ClientNotFoundException();
        }
    }

}
