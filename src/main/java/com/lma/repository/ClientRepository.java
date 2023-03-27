package com.lma.repository;

import com.lma.model.Book;
import com.lma.model.Client;

import java.util.HashSet;
import java.util.Set;

public interface ClientRepository {

    Boolean addClient(Client client);

    void loadClientData();

    Client getClientByFullName(String fullName);

    Client getClientByBook(Book book);

    Client getClientByLastName(String lastName);

    Client getClientByFirstName(String firstName);

    Set<Client> getClients();
}
