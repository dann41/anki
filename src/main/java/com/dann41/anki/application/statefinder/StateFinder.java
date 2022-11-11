package com.dann41.anki.application.statefinder;

import com.dann41.anki.domain.deck.Deck;
import com.dann41.anki.domain.deck.DeckId;
import com.dann41.anki.domain.deck.DeckNotFoundException;
import com.dann41.anki.domain.deck.DeckRepository;

public class StateFinder {
  private final DeckRepository deckRepository;

  public StateFinder(DeckRepository deckRepository) {
    this.deckRepository = deckRepository;
  }

  public StateResponse execute(String deckId) {
    DeckId id = new DeckId(deckId);
    Deck deck = deckRepository.findById(id);
    if (deck == null) {
      throw new DeckNotFoundException(id);
    }
    return new StateResponse(
        deck.unplayedCards().size(),
        deck.cardsInGreenBox().size(),
        deck.cardsInOrangeBox().size(),
        deck.cardsInRedBox().size(),
        deck.session()
    );
  }
}
