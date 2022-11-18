package com.dann41.anki.core.deck.domain;

public class DeckFinder {

  private final DeckRepository deckRepository;

  public DeckFinder(DeckRepository deckRepository) {
    this.deckRepository = deckRepository;
  }

  public Deck execute(DeckId deckId) {
    Deck deck = deckRepository.findById(deckId);
    if (deck == null) {
      throw new DeckNotFoundException(deckId);
    }
    return deck;
  }

}
