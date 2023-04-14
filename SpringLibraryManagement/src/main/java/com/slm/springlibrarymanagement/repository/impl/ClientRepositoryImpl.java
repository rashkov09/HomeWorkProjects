package com.slm.springlibrarymanagement.repository.impl;

import com.slm.springlibrarymanagement.constants.ClassesEnum;
import com.slm.springlibrarymanagement.exceptions.BackUpFailedException;
import com.slm.springlibrarymanagement.model.entities.Client;
import com.slm.springlibrarymanagement.repository.ClientRepository;
import com.slm.springlibrarymanagement.service.dmo.DataLoaderService;
import com.slm.springlibrarymanagement.service.dmo.DataWriterService;
import com.slm.springlibrarymanagement.service.dmo.impl.DataLoaderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Repository
public class ClientRepositoryImpl implements ClientRepository {
    private static final String INSERT_CLIENT_SQL = "INSERT INTO slm.clients (first_name,last_name,address,phone_number) VALUES (?,?,?,?)";
    private static final String SELECT_CLIENTS_SQL =  "SELECT * FROM slm.clients";
    private static List<Client> clientList;
    private final DataLoaderService<Client> dataLoaderService;
    private final DataWriterService<Client> dataWriterService;

    @Autowired
    public ClientRepositoryImpl(DataLoaderServiceImpl<Client> dataLoaderService, DataWriterService<Client> dataWriterService) {
        this.dataLoaderService = dataLoaderService;
        this.dataWriterService = dataWriterService;
        clientList = new ArrayList<>();
    }

    @Override
    public void loadClients() throws SQLException {
        clientList = dataLoaderService.loadDataFromDb(SELECT_CLIENTS_SQL, ClassesEnum.Client);
        if (clientList.isEmpty()) {
            clientList = dataLoaderService.loadDataFromFile(ClassesEnum.Client);
            addAll();
        }
    }

    @Override
    public void backupToFile() throws BackUpFailedException {
        dataWriterService.writeDataToFile(clientList, ClassesEnum.Client);
    }

    private void addAll() throws SQLException {
        dataWriterService.saveAll(INSERT_CLIENT_SQL, clientList, ClassesEnum.Client);
    }

    @Override
    public Client findByPhoneNumber(String phoneNumber) {
        return clientList.stream().filter(client -> client.getPhoneNumber().equals(phoneNumber)).findFirst().orElseThrow();
    }

    @Override
    public List<Client> findByFirstName(String firstName) {
        return clientList.stream().filter(client -> client.getFirstName().equalsIgnoreCase(firstName)).collect(Collectors.toList());
    }

    @Override
    public List<Client> findByLastName(String lastName) {
        return clientList.stream().filter(client -> client.getLastName().equalsIgnoreCase(lastName)).collect(Collectors.toList());
    }

    @Override
    public Client findClientByFirstNameAndLastName(String fullName) throws NoSuchElementException {
        return clientList.stream().filter(client -> client.fullName().equalsIgnoreCase(fullName)).findFirst().orElseThrow();
    }

    @Override
    public List<Client> findAll() {
        return clientList;
    }

    @Override
    public boolean addClient(Client client) {
        Long id = dataWriterService.save(INSERT_CLIENT_SQL, client);
        if (id != 0L) {
            client.setId(id);
            clientList.add(client);
            return true;
        }
        return false;
    }

    @Override
    public Client findById(Long clientId) throws NoSuchElementException {
        return clientList.stream().filter(client -> client.getId().equals(clientId)).findFirst().orElseThrow();

    }
}
