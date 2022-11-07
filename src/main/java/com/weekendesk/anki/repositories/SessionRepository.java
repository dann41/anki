package com.weekendesk.anki.repositories;

public interface SessionRepository {

    /**
     * Return the epoch day of last session
     * @return epoch day of last session
     */
    long getLastSessionStarted();

    /**
     * Updates the day of current session
     * @param epochDay epoch day of current session
     */
    void setLastSessionStarted(long epochDay);

}
