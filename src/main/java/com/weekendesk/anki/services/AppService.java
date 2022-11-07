package com.weekendesk.anki.services;

import com.weekendesk.anki.domain.AppState;

public interface AppService {

    /**
     * Loads the state of the application
     */
    void loadState();

    /**
     * Saves the state of the application
     */
    void saveState();

    /**
     * Return a summary of the application state:
     * <ul>
     *     <li>Number of cards in each box</li>
     *     <li>Day of last session</li>
     * </ul>
     * @return the application state
     */
    AppState getState();

    /**
     * Checks if there are cards in the system
     * @return if there are cards in the system
     */
    boolean hasAnyCard();

    /**
     * Checks if the user can keep studying of if the training has been completed
     * @return if the user has completed the training
     */
    boolean isTrainingComplete();

    /**
     * Checks if the user has trained today
     * @return if the user has trained today
     */
    boolean hasTrainedToday();

}
