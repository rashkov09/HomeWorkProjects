package com.slm.springlibrarymanagement.mappers;

import com.slm.springlibrarymanagement.model.entities.Client;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientRowMapper implements RowMapper<Client> {
    @Override
    public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
        Client client = new Client();
        client.setId(rs.getLong("id"));
        client.setFirstName(rs.getString("first_name"));
        client.setLastName(rs.getString("last_name"));
        client.setAddress(rs.getString("address"));
        client.setPhoneNumber(rs.getString("phone_number"));
        return client;
    }
}
