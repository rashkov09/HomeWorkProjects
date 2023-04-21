package com.slm.springlibrarymanagement.mappers;

import com.slm.springlibrarymanagement.model.dto.ClientDto;
import com.slm.springlibrarymanagement.model.entities.Client;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ClientMapper implements RowMapper<Client> {

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


    public List<ClientDto> mapToDtoList(List<Client> inboundList) {
        return inboundList.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public ClientDto mapToDto(Client inboundClient) {
        return new ClientDto(inboundClient.getId(), inboundClient.getFirstName(), inboundClient.getLastName(), inboundClient.getAddress(), inboundClient.getPhoneNumber());
    }

    public Client mapFromDto(ClientDto inboundClientDto) {
        Client client = new Client();
        client.setId(inboundClientDto.getId());
        client.setFirstName(inboundClientDto.getFirstName());
        client.setLastName(inboundClientDto.getLastName());
        client.setAddress(inboundClientDto.getAddress());
        client.setPhoneNumber(inboundClientDto.getPhoneNumber());
        return client;
    }
}
