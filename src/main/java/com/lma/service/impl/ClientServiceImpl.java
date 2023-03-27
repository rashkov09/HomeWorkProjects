package com.lma.service.impl;

import com.lma.model.Book;
import com.lma.model.Client;
import com.lma.repository.ClientRepository;
import com.lma.repository.impl.ClientRepositoryImpl;
import com.lma.service.BookService;
import com.lma.service.ClientService;

import java.util.NoSuchElementException;

import static com.lma.constatns.CustomMessages.NO_SUCH_CLIENT_EXCEPTION;

public class ClientServiceImpl implements ClientService {
    private final static ClientRepository clientRepository = new ClientRepositoryImpl();
    private final static BookService bookService = new BookServiceImpl();

    public ClientServiceImpl() {
        seedClients();
    }

    @Override
    public void seedClients() {
        clientRepository.loadClientData();
    }

    @Override
    public void addClient(String firstName, String lastName) {
        // TODO to validate user input
        Client client = new Client(firstName, lastName);
        clientRepository.addClient(client);
    }

    @Override
    public Client getClientByFullName(String fullName) {
        return clientRepository.getClientByFullName(fullName);

    }

    @Override
    public String getClientByBookName(String bookName) {
        Book book = bookService.getBook(bookName);

        return clientRepository.getClientByBook(book).toString();
    }

    @Override
    public String getClientByLastName(String lastName) {
        StringBuilder builder = new StringBuilder();
        clientRepository.getClientByLastName(lastName).forEach(client -> builder.append(client.toString()).append("\n"));
        return builder.toString();
    }

    @Override
    public String getClientByFirstName(String firstName) {
        StringBuilder builder = new StringBuilder();
        clientRepository.getClientByFirstName(firstName).forEach(client -> builder.append(client.toString()).append("\n"));
        return builder.toString();
    }

    @Override
    public String getClients() {
        StringBuilder builder = new StringBuilder();

        clientRepository.getClients().forEach(client ->
                builder
                        .append(client.toString())
                        .append("\n")
        );

        return builder.toString();
    }
}
