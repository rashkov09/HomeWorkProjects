package com.scalefocus.midterm.trippyapp.repository;

import com.scalefocus.midterm.trippyapp.model.User;

import java.sql.SQLException;
import java.util.List;

public interface Repository<T> {

    List<T> getAll();
    Long add(T object) throws SQLException;
    User update(T object, Long id) throws SQLException;
    Boolean delete(T object);

    T getById(Long id);
    T getByEmail(String email);
    T getByUsername(String username);

}
