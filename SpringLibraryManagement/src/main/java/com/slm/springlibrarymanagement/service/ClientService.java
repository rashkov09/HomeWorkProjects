package com.slm.springlibrarymanagement.service;

import com.slm.springlibrarymanagement.exceptions.BackUpFailedException;
import com.slm.springlibrarymanagement.exceptions.FileForEntityNotFound;
import com.slm.springlibrarymanagement.exceptions.NoEntriesFoundException;
import com.slm.springlibrarymanagement.exceptions.client.*;
import com.slm.springlibrarymanagement.model.entities.Client;

public interface ClientService {
    String findAllClients() throws NoEntriesFoundException;

    void importClients() throws FileForEntityNotFound;

    void backupToFile() throws BackUpFailedException;

    String insertClient(String firstName, String lastName, String address, String phoneNumber) throws InvalidClientFirstNameException, InvalidClientLastNameException, InvalidClientPhoneException, ClientAlreadyExistsException;

    Client findClientByPhoneNumber(String phoneNumber) throws ClientNotFoundException, InvalidClientPhoneException;

    String findClientsByFirstName(String firstName) throws InvalidClientFirstNameException, ClientNotFoundException;

    String findClientsByLastName(String lastName) throws InvalidClientLastNameException, ClientNotFoundException;
}
