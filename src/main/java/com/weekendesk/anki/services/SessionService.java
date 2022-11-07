package com.weekendesk.anki.services;

public interface SessionService {

    /**
     * Returns the last epoch day the user performed a training session
     * @return epoch day of last session
     */
    long getLastSessionStarted();

    /**
     * Starts a new session
     */
    void startSession();

    /**
     * Finishes the current session
     */
    void finishSession();
}
