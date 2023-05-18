package com.dann41.anki.api.infrastructure.controllers.mydecks;

import com.dann41.anki.core.deck.application.alldecksfinder.DeckSummary;

import java.time.LocalDate;

public record DeckDto(String id, int numberOfQuestions, LocalDate lastSession) {
    public static DeckDto fromSummary(DeckSummary deckSummary) {
        return new DeckDto(
                deckSummary.id(),
                deckSummary.numberOfQuestions(),
                deckSummary.lastSession()
        );
    }
}
