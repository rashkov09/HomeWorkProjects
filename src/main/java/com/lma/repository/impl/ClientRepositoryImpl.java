package com.lma.repository.impl;

import com.lma.model.Book;
import com.lma.model.Client;
import com.lma.repository.ClientRepository;
import com.lma.util.ClientFileAccessor;
import com.lma.util.ClientMapper;

import java.io.IOException;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientRepositoryImpl implements ClientRepository {
    private static final ClientFileAccessor clientFileAccessor = new ClientFileAccessor();
    private static final HashSet<Client> clientList = new HashSet<>();


    @Override
    public void loadClientData() {
        clientFileAccessor.readAllLines().forEach(line -> clientList.add(ClientMapper.mapClientFromString(line)));
    }

    @Override
    public Client getClientByFullName(String fullName) {
        return clientList.stream().filter(client -> client.getFullName().equals(fullName)).findAny().orElse(null);
    }

    @Override
    public Client getClientByBook(Book book) throws NoSuchElementException {
        return clientList.stream().filter(client -> client.getBooks().contains(book)).findFirst().orElseThrow();
    }

    @Override
    public Set<Client> getClientByLastName(String lastName)  {
        return clientList.stream().filter(client -> client.getLastName().equals(lastName)).collect(Collectors.toSet());
    }

    @Override
    public Set<Client> getClientByFirstName(String firstName)  {
        return clientList.stream().filter(client -> client.getFirstName().equals(firstName)).collect(Collectors.toSet());
    }

    @Override
    public HashSet<Client> getClients() {
        return clientList;
    }

    @Override
    public Boolean addClient(Client client) {
        if (clientList.add(client)) {
            try {
                clientFileAccessor.writeLine(ClientMapper.mapClientToString(client));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        }
        return false;
    }
}
