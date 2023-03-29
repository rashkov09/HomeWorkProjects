package com.lma.repository.impl;

import com.lma.accessor.ClientFileAccessor;
import com.lma.mapper.ClientMapper;
import com.lma.model.Book;
import com.lma.model.Client;
import com.lma.repository.ClientRepository;

import java.io.IOException;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import static com.lma.constants.CustomMessages.FILE_NOT_FOUND_MESSAGE;
import static com.lma.constants.Paths.CLIENT_FILE_PATH;


public class ClientRepositoryImpl implements ClientRepository {
    private static final ClientFileAccessor clientFileAccessor = new ClientFileAccessor();
    private static final Set<Client> clientList = new HashSet<>();


    @Override
    public void loadClientData() {
        try {
            clientFileAccessor.readAllLines().forEach(line -> clientList.add(ClientMapper.mapClientFromString(line)));
        }catch (Exception e){
            System.out.printf(FILE_NOT_FOUND_MESSAGE,CLIENT_FILE_PATH);
        }
    }

    @Override
    public Client getClientByFullName(String fullName) {
        return clientList.stream().filter(client -> client.getFullName().equalsIgnoreCase(fullName)).findAny().orElse(null);
    }

    @Override
    public Client getClientByBook(Book book) throws NoSuchElementException {
        return clientList.stream().filter(client -> client.getBooks().contains(book)).findFirst().orElseThrow();
    }

    @Override
    public Set<Client> getClientByLastName(String lastName)  {
        return clientList.stream().filter(client -> client.getLastName().equalsIgnoreCase(lastName)).collect(Collectors.toSet());
    }

    @Override
    public Set<Client> getClientByFirstName(String firstName)  {
        return clientList.stream().filter(client -> client.getFirstName().equalsIgnoreCase(firstName)).collect(Collectors.toSet());
    }

    @Override
    public Set<Client> getClients() {
        return clientList;
    }

    @Override
    public Client getClient(String clientName) {
        return clientList.stream().filter(client -> client.getFullName().equals(clientName)).findFirst().orElseThrow();
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
