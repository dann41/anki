package com.dann41.anki.api.infrastructure.controllers.deck.getstate;

import java.time.LocalDate;

public record GetDeckStateResponse(
    String deckId,
    int totalCards,
    int unplayedCards,
    int greenCards,
    int orangeCards,
    int redCards,
    LocalDate lastSession
) {
}
