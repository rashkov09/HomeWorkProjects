package com.scalefocus.midterm.trippyapp.repository;

import com.scalefocus.midterm.trippyapp.model.User;

import java.sql.SQLException;

public interface Repository<T> {

    Long add(T object) throws SQLException;
    User update(T object, Long id);
    Boolean delete(T object);

    T getById(Long id);

}
