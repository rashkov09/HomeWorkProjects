package com.slm.springlibrarymanagement.repository.impl;

import com.slm.springlibrarymanagement.constants.ClassesEnum;
import com.slm.springlibrarymanagement.model.entities.Client;
import com.slm.springlibrarymanagement.service.dmo.impl.DataLoaderServiceImpl;
import com.slm.springlibrarymanagement.service.dmo.impl.DataWriterServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClientRepositoryImplTest {
    private final List<Client> clientList = new ArrayList<>();
    @Mock
    private DataLoaderServiceImpl<Client> clientDataLoaderService;
    @Mock
    private DataWriterServiceImpl<Client> clientDataWriterService;
    @InjectMocks
    private ClientRepositoryImpl clientRepository;

    @Before
    public void setUp() throws SQLException {
        Client client = new Client();
        client.setFirstName("Damon");
        client.setLastName("Sgorish");
        client.setAddress("Russia");
        client.setPhoneNumber("+35345665555");
        clientList.add(client);
        when(clientDataLoaderService.loadDataFromFile(ClassesEnum.Client)).thenReturn(clientList);
        clientRepository.loadClients();
    }

    @Test
    public void testFindAllClients() {
        List<Client> current = clientRepository.findAllClients();
        Assert.assertTrue(current.size() > 0);
        Assert.assertEquals(current.get(0).fullName(), "Damon Sgorish");
    }

    @Test(expected = NoSuchElementException.class)
    public void findByPhoneNumberThrowsException() {
        clientRepository.findByPhoneNumber("3123123213213");
    }

    @Test
    public void findByFirstNameThrowsException() {
        List<Client> current = clientRepository.findByFirstName("Blyat");
        Assert.assertTrue(current.isEmpty());
    }

    @Test
    public void findByFirstNameReturnsData() {
        List<Client> current = clientRepository.findByFirstName("Damon");
        Assert.assertEquals(1, current.size());
        Assert.assertEquals("Damon", current.get(0).getFirstName());
    }

    @Test
    public void findByLastNameReturnsEmpty() {
        List<Client> current = clientRepository.findByLastName("Blyat");
        Assert.assertTrue(current.isEmpty());
    }

    @Test
    public void findByLastNameThrowsException() {
        List<Client> current = clientRepository.findByLastName("Sgorish");
        Assert.assertEquals(1, current.size());
        Assert.assertEquals("Sgorish", current.get(0).getLastName());
    }

    @Test(expected = NoSuchElementException.class)
    public void findClientByFirstNameAndLastNameThrowsException() {
        clientRepository.findClientByFirstNameAndLastName("Blyat");
    }

    @Test
    public void findClientByFirstNameAndLastNameReturnsValidData() {
        Client client = clientRepository.findClientByFirstNameAndLastName("Damon Sgorish");
        Assert.assertEquals("Damon", client.getFirstName());
        Assert.assertEquals("Sgorish", client.getLastName());
        Assert.assertEquals("Damon Sgorish", client.fullName());
    }

    @Test
    public void addClient() {
        Client client = new Client();
        clientRepository.addClient(client);
        verify(clientDataWriterService).save(any(), any());
    }

}