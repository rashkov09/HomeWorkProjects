package com.lma.repository.impl;

import com.lma.model.Client;
import com.lma.repository.ClientRepository;
import com.lma.util.ClientFileAccessor;
import com.lma.util.ClientMapper;

import java.io.IOException;
import java.util.HashSet;

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
