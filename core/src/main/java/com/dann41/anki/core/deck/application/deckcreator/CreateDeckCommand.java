package com.dann41.anki.core.deck.application.deckcreator;

public record CreateDeckCommand(String deckId, String userId, String collectionId) {
}
