package com.lma.repository;

import com.lma.model.Client;

public interface ClientRepository {

    Boolean addClient(Client client);

    void loadClientData();

    Client getClientByFullName(String fullName);
}
