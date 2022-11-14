package com.dann41.anki.core.application.deck.alldecksfinder;

import java.time.LocalDate;

public record DeckSummary(String id, int numberOfQuestions, LocalDate lastSession) {
}
