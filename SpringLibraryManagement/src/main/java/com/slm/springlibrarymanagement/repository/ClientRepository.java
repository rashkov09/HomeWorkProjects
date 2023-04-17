package com.slm.springlibrarymanagement.repository;

import com.slm.springlibrarymanagement.exceptions.BackUpFailedException;
import com.slm.springlibrarymanagement.model.entities.Client;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;


public interface ClientRepository {

    void loadClients() throws SQLException;

    void backupToFile() throws BackUpFailedException;

    Client findByPhoneNumber(String phoneNumber);

    List<Client> findByFirstName(String firstName);

    List<Client> findByLastName(String lastName);

    Client findClientByFirstNameAndLastName(String fullName);

    List<Client> findAllClients();

    boolean addClient(Client client);

    Client findById(Long clientId) throws NoSuchElementException;


}
