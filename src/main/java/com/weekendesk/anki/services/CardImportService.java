package com.weekendesk.anki.services;

public interface CardImportService {

    /**
     * Imports a list of cards from an external resource
     * @param resourceName the name of the external resource
     * @return number of the cards imported
     */
    long importFromResource(String resourceName);
}
