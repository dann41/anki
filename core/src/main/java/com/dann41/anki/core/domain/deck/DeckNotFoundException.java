package com.dann41.anki.core.domain.deck;

public class DeckNotFoundException extends RuntimeException {

  private final DeckId deckId;

  public DeckNotFoundException(DeckId deckId) {
    super("Deck not found for id " + deckId.value());
    this.deckId = deckId;
  }

  public DeckId deckId() {
    return deckId;
  }
}
