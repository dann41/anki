package com.weekendesk.anki.helpers;

import java.util.Set;

public interface ReaderHelper {

    /**
     * Displays a message, reads a line from Console and returns the user input
     * @param message message to output before reading
     * @return the input value or null in case of exception
     */
    String readLine(String message);

    /**
     * Asks for user input until it reads an expected value. If the input is not as expected,
     * it displays the invalidOptionMessage
     * @param invalidOptionMessage message to display if the input is wrong
     * @param expectedValues list of valid inputs values
     * @return an expected value or null in case of exception
     */
    String readOption(String invalidOptionMessage, Set<String> expectedValues);
}
