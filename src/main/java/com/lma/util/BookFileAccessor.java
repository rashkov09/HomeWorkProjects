package com.lma.util;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

import static com.lma.constatns.Constants.FILE_NOT_FOUND_MESSAGE;
import static com.lma.constatns.Paths.BOOKS_FILE_PATH;


public class BookFileAccessor {
    private final static BufferedWriter writer;
    private final static BufferedReader reader;

    static {
        try {
            writer = new BufferedWriter(new FileWriter(BOOKS_FILE_PATH, true));
            reader = new BufferedReader(new FileReader(BOOKS_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException(String.format(FILE_NOT_FOUND_MESSAGE,BOOKS_FILE_PATH),e);
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
