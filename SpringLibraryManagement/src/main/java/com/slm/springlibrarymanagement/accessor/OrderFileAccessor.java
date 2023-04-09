package com.slm.springlibrarymanagement.accessor;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

import static com.slm.springlibrarymanagement.constants.Paths.CLIENT_FILE_PATH;
import static com.slm.springlibrarymanagement.constants.Paths.ORDERS_FILE_PATH;

public class OrderFileAccessor {
    private final static BufferedWriter writer;
    private final static BufferedReader reader;


    static {
        try {
            writer = new BufferedWriter(new FileWriter(ORDERS_FILE_PATH, true));
            reader = new BufferedReader(new FileReader(ORDERS_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException(String.format("File not with path %s found", CLIENT_FILE_PATH), e);
        }
    }

    public List<String> readAllLines() {
        return reader.lines().collect(Collectors.toList());

    }

    public void writeLine(String line) throws IOException {
        try {
            writer.append(line).append("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            writer.flush();
        }
    }

    public void replaceLine(String originalLineText, String newLineText, List<String> currentOrders) throws IOException {
        BufferedWriter editWriter = new BufferedWriter(new FileWriter(ORDERS_FILE_PATH));
        StringBuilder builder = new StringBuilder();
        try {
            List<String> list = currentOrders.stream().map(line -> line.equals(originalLineText) ? newLineText : line)
                    .toList();
            list.forEach(line -> {
                builder.append(line).append("\n");
            });
            editWriter.write(builder.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            editWriter.flush();
        }
    }
}