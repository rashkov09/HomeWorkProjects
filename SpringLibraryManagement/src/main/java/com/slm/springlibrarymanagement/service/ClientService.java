package com.slm.springlibrarymanagement.service;

import com.slm.springlibrarymanagement.exceptions.BackUpFailedException;
import com.slm.springlibrarymanagement.exceptions.InvalidIdException;
import com.slm.springlibrarymanagement.exceptions.NoEntriesFoundException;
import com.slm.springlibrarymanagement.exceptions.author.AuthorNotFoundException;
import com.slm.springlibrarymanagement.exceptions.client.*;
import com.slm.springlibrarymanagement.model.dto.ClientDto;
import com.slm.springlibrarymanagement.model.entities.Client;

import java.sql.SQLException;
import java.util.List;

public interface ClientService {
    List<ClientDto> findAllClients();

    void backupToFile() throws BackUpFailedException;

    String insertClient(String firstName, String lastName, String address, String phoneNumber) throws InvalidClientFirstNameException, InvalidClientLastNameException, InvalidClientPhoneException, ClientAlreadyExistsException;

    Client findClientByPhoneNumber(String phoneNumber) throws ClientNotFoundException, InvalidClientPhoneException;

    String findClientsByFirstName(String firstName) throws InvalidClientFirstNameException, ClientNotFoundException;

    String findClientsByLastName(String lastName) throws InvalidClientLastNameException, ClientNotFoundException;

    Client findClientByFullName(String fullName) throws ClientNotFoundException;

    ClientDto findClientById(String clientId);

    void loadClientData() throws AuthorNotFoundException, SQLException, InvalidIdException;
}
