package com.weekendesk.anki.repositories;

import com.weekendesk.anki.domain.Card;
import com.weekendesk.anki.domain.Box;

import java.util.List;

public interface CardRepository {

    /**
     * Inserts a card into the system
     * @param card card to insert
     * @return true if the card was successfully added, false otherwise
     */
    boolean insertCard(Card card);

    /**
     * Update a card
     * @param card card to update
     */
    void updateCard(Card card);

    /**
     * Moves all cards in Box 'from' to Box 'to'
     * @param from source box
     * @param to target box
     */
    void moveAllCardsToBox(Box from, Box to);

    /**
     * Return all cards in the system
     * @return list of cards
     */
    List<Card> getDeck();

    /**
     * Return all cards in a box
     * @param box a box
     * @return cards within a box
     */
    List<Card> findCardsInBox(Box box);
}
