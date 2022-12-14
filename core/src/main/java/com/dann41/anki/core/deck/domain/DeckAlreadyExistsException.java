package com.dann41.anki.core.deck.domain;

public class DeckAlreadyExistsException extends RuntimeException {

  private final DeckId deckId;

  public DeckAlreadyExistsException(DeckId deckId) {
    super("Deck already exists for id " + deckId.value());
    this.deckId = deckId;
  }

  public DeckId deckId() {
    return deckId;
  }
}