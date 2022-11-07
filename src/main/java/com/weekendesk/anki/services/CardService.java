package com.weekendesk.anki.services;

import com.weekendesk.anki.domain.Card;
import com.weekendesk.anki.domain.Box;

import java.util.Queue;

public interface CardService {

    /**
     * Inserts a card
     * @param card card to insert
     * @return
     */
    boolean addCard(Card card);

    /**
     * Count the number of cards in a box
     * @param box a box
     * @return number of cards in the box
     */
    long countCards(Box box);

    /**
     * Retrieves cards within a box, alphabetically ordered by question
     * @param box a box
     * @return cards in the box
     */
    Queue<Card> getCardsSorted(Box box);

    /**
     * Moves a card to a new box
     * @param card the card to move
     * @param box the target box
     */
    void moveCardToBox(Card card, Box box);

    /**
     * Performs the daily rotation
     * moving cards in orange box to red box
     * and cards in green box to orange box
     */
    void rotateCards();
}
