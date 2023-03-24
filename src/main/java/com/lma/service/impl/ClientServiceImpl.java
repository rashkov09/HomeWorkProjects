package com.lma.service.impl;

import com.lma.model.Client;
import com.lma.repository.ClientRepository;
import com.lma.repository.impl.ClientRepositoryImpl;
import com.lma.service.ClientService;

public class ClientServiceImpl implements ClientService {
    private  final static ClientRepository clientRepository = new ClientRepositoryImpl();

    @Override
    public void seedClients() {
        clientRepository.loadClientData();
    }

    @Override
    public void addClient(String firstName, String lastName) {
        // TODO to validate user input
        Client client = new Client(firstName,lastName);
        clientRepository.addClient(client);
    }

    @Override
    public Client getClientByFullName(String fullName) {
        return clientRepository.getClientByFullName(fullName);
    }
}
