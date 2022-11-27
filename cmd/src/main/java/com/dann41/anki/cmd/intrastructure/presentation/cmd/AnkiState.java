package com.dann41.anki.cmd.intrastructure.presentation.cmd;

import java.time.LocalDate;

public record AnkiState(
    String deckId,
    int totalCards,
    int cardsInRedBox,
    int cardsInOrangeBox,
    int cardsInGreenBox,
    LocalDate lastPlayed) {
}
