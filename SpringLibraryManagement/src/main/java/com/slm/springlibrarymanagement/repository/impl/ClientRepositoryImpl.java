package com.slm.springlibrarymanagement.repository.impl;

import com.slm.springlibrarymanagement.model.entities.Client;
import com.slm.springlibrarymanagement.repository.ClientRepository;
import com.slm.springlibrarymanagement.service.dmo.DataLoaderService;
import com.slm.springlibrarymanagement.service.dmo.impl.DataLoaderServiceImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
public class ClientRepositoryImpl implements ClientRepository {
    private static List<Client> clientList;
    private final DataLoaderService<Client> dataLoaderService;

    public ClientRepositoryImpl(DataLoaderServiceImpl<Client> dataLoaderService) {
        this.dataLoaderService = dataLoaderService;
        clientList = new ArrayList<>();
    }

    @Override
    public void loadClients() {
        String sql = "SELECT * FROM slm.clients";
        clientList = dataLoaderService.loadData(sql, new Client());
    }

    @Override
    public Client findByPhoneNumber(String phoneNumber) {
        return null;
    }

    @Override
    public Set<Client> findByFirstName(String firstName) {
        return null;
    }

    @Override
    public Set<Client> findByLastName(String lastName) {
        return null;
    }

    @Override
    public Client findClientByFirstNameAndLastName(String firstName, String lastName) {
        return null;
    }

    @Override
    public List<Client> findAll() {
        return clientList;
    }

    @Override
    public void saveAll(List<Client> clientList) {

    }

    @Override
    public void addClient(Client client) {

    }

    @Override
    public Client findById(Long clientId) {
        return null;

    }
}
