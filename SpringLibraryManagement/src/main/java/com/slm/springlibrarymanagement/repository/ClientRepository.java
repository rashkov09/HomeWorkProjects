package com.slm.springlibrarymanagement.repository;

import com.slm.springlibrarymanagement.model.entities.Client;

import java.util.List;
import java.util.Set;


public interface ClientRepository {

    void loadClients();

    Client findByPhoneNumber(String phoneNumber);

    Set<Client> findByFirstName(String firstName);

    Set<Client> findByLastName(String lastName);

    Client findClientByFirstNameAndLastName(String firstName, String lastName);

    List<Client> findAll();

    void saveAll(List<Client> clientList);

    void addClient(Client client);

    Client findById(Long clientId);
}
