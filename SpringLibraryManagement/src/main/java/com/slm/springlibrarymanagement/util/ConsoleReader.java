package com.slm.springlibrarymanagement.util;

import java.util.Scanner;

public class ConsoleReader {
    private static final String INVALID_INT = "%s is not an int. Try again: ";
    private final Scanner scanner;

    public ConsoleReader(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readString() {
        return scanner.nextLine();
    }

    public int readInt() {
        while (!scanner.hasNextInt()) {
            String input = scanner.next();
            System.out.printf(INVALID_INT, input);
        }
        int input = scanner.nextInt();
        scanner.nextLine();
        return input;
    }

    public Long readLong() {
        while (!scanner.hasNextLong()) {
            String input = scanner.next();
            System.out.printf(INVALID_INT, input);
        }
        long input = scanner.nextLong();
        scanner.nextLine();
        return input;
    }

}
