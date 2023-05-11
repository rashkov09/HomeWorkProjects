package com.slm.springlibrarymanagement.repository;

import com.slm.springlibrarymanagement.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User getUserByUsername(String username);
}
