package com.dann41.anki.core.deck.application.statefinder;

import com.dann41.anki.shared.application.QueryResponse;

import java.time.LocalDate;

public record FindStatusResponse(int unplayedCards, int greenCards, int orangeCards, int redCards,
                                 LocalDate lastSession) implements QueryResponse {

    public int totalCards() {
        return unplayedCards + greenCards + orangeCards + redCards;
    }

}
