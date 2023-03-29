package com.lma.service;

import com.lma.model.Client;

public interface ClientService {

    void seedClients();
    String addClient(String firstName, String lastName);

    Client getClientByFullName(String subValue);

    String getClientByBookName(String bookName);

    String getClientByLastName(String lastName);

    String getClientByFirstName(String firstName);

    String getClients();
}
