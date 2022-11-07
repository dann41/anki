package com.weekendesk.anki.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

public class ConsoleReaderHelper implements ReaderHelper {

    private final BufferedReader reader;

    public ConsoleReaderHelper() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public String readLine(String message) {
        return readLine(reader, message);
    }

    String readLine(BufferedReader reader, String message) {
        try {
            System.out.print(message);
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String readOption(String invalidOptionMessage, Set<String> expectedValues) {
        return readOption(reader, invalidOptionMessage, expectedValues);
    }

    String readOption(BufferedReader reader, String invalidOptionMessage, Set<String> expectedValues) {
        String value;
        try {
            value = reader.readLine();
            while (!expectedValues.contains(value)) {
                System.out.print(invalidOptionMessage);
                value = reader.readLine();
            }

        } catch (IOException e) {
            value = null;
            e.printStackTrace();
        }
        return value;
    }
}
