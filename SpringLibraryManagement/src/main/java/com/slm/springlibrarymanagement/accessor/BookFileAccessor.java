package com.slm.springlibrarymanagement.accessor;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

import static com.slm.springlibrarymanagement.constants.Paths.*;

@Component
public class BookFileAccessor {

    private final static BufferedReader reader;

    static {
        try {
            reader = new BufferedReader(new FileReader(BOOKS_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException(String.format("File not with path %s found",BOOKS_FILE_PATH),e);
        }
    }

    public List<String> readAllLines(){
        return reader.lines().collect(Collectors.toList());
    }

    public void writeLine(String line) throws IOException {
        BufferedWriter backupWriter = new BufferedWriter(new FileWriter(BOOKS_FILE_PATH));
        try {
            backupWriter.append(line).append("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            backupWriter.flush();
        }
    }
}
