package com.slm.springlibrarymanagement.accessor;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

import static com.slm.springlibrarymanagement.constants.Paths.CLIENT_FILE_PATH;
import static com.slm.springlibrarymanagement.constants.Paths.ORDERS_FILE_PATH;

@Component
public class OrderFileAccessor {
    private final static BufferedReader reader;


    static {
        try {
            reader = new BufferedReader(new FileReader(ORDERS_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException(String.format("File not with path %s found", CLIENT_FILE_PATH), e);
        }
    }

    public List<String> readAllLines() {
        return reader.lines().collect(Collectors.toList());

    }

    public void writeLine(String line) throws IOException {
        BufferedWriter backupWriter = new BufferedWriter(new FileWriter(ORDERS_FILE_PATH));
        try {
            backupWriter.append(line).append("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            backupWriter.flush();
        }
    }
}