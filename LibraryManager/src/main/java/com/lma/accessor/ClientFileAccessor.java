package com.lma.accessor;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

import static com.lma.constants.CustomMessages.FILE_NOT_FOUND_MESSAGE;
import static com.lma.constants.Paths.CLIENT_FILE_PATH;
public class ClientFileAccessor {
    private final static BufferedWriter writer;
    private final static BufferedReader reader;

    static {
        try {
            writer = new BufferedWriter(new FileWriter(CLIENT_FILE_PATH,true));
            reader = new BufferedReader(new FileReader(CLIENT_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException(String.format(FILE_NOT_FOUND_MESSAGE,CLIENT_FILE_PATH),e);
        }
    }

    public List<String> readAllLines(){
        return reader.lines().collect(Collectors.toList());
    }

    public void writeLine(String line) throws IOException {
        try {
            writer.append(line).append("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            writer.flush();
        }
    }
}
