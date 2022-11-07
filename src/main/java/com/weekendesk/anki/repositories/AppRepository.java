package com.weekendesk.anki.repositories;

public interface AppRepository {

    /**
     * Restores the state of the application
     */
    void load();

    /**
     * Stores the state of the application
     */
    void save();

}
