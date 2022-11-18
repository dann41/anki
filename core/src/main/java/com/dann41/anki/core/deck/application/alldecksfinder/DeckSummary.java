package com.dann41.anki.core.deck.application.alldecksfinder;

import java.time.LocalDate;

public record DeckSummary(String id, int numberOfQuestions, LocalDate lastSession) {
}
