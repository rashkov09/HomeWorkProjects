package com.lma.util;

import com.lma.model.Client;

public class ClientMapper {

    public static Client mapClientFromString(String value){
        String[] values = value.split("\\s");
        String firstName = values[0];
        String lastName = values[1];
        return new Client(firstName,lastName);
    }

    public static String mapClientToString(Client client) {
        return String.format("%s %s",client.getFirstName(),client.getLastName());
    }

}
