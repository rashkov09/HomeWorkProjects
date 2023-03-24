package com.lma.service;

import com.lma.model.Client;

public interface ClientService {

    void seedClients();
    void addClient(String firstName, String lastName);

    Client getClientByFullName(String subValue);
}
