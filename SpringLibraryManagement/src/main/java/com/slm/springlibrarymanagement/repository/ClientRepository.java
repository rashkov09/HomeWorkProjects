package com.slm.springlibrarymanagement.repository;

import com.slm.springlibrarymanagement.model.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByPhoneNumber(String phoneNumber);

    Set<Client> findByFirstName(String firstName);

    Set<Client>  findByLastName(String lastName);

    Client findClientByFirstNameAndLastName(String firstName,String lastName);
}
