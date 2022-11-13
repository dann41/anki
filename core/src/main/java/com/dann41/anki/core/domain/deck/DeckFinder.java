package com.dann41.anki.core.domain.deck;

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
