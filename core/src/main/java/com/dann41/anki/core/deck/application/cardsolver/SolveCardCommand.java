package com.dann41.anki.core.deck.application.cardsolver;

public record SolveCardCommand(String deckId, String userId, String cardId, String boxName) {
}
