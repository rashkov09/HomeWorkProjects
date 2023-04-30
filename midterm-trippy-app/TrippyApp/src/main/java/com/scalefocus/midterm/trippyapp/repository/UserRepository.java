package com.scalefocus.midterm.trippyapp.repository;

import com.scalefocus.midterm.trippyapp.model.User;

public interface UserRepository extends CustomRepository<User> {
    User getByEmail(String email);

    User getByUsername(String username);
}
