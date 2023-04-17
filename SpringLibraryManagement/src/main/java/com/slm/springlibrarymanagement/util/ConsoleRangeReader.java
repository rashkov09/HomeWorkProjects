package com.slm.springlibrarymanagement.util;

public class ConsoleRangeReader {

    private static final String INVALID_RANGE_MESSAGE = "Min value cannot exceed max value";
    private static final String NOT_WITHIN_RANGE_MESSAGE = "Input must be between %d and %d\n";

    private final ConsoleReader consoleReader;

    public ConsoleRangeReader(ConsoleReader consoleReader) {
        this.consoleReader = consoleReader;
    }

    public int readInt(int minValue, int maxValue) {
        if (minValue > maxValue) {
            throw new IllegalArgumentException(INVALID_RANGE_MESSAGE);
        }
        int input;
        boolean isWithinRange;
        do {
            input = consoleReader.readInt();
            isWithinRange = input >= minValue && input <= maxValue;
            if (!isWithinRange) {
                System.out.printf(NOT_WITHIN_RANGE_MESSAGE, minValue, maxValue);
            }
        } while (!isWithinRange);
        return input;
    }
}
