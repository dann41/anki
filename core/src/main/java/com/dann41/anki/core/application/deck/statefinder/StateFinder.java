package com.dann41.anki.core.application.deck.statefinder;

import com.dann41.anki.core.domain.deck.Deck;
import com.dann41.anki.core.domain.deck.DeckId;
import com.dann41.anki.core.domain.deck.DeckNotFoundException;
import com.dann41.anki.core.domain.deck.DeckRepository;

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
        deck.unansweredCards().size(),
        deck.cardsInGreenBox().size(),
        deck.cardsInOrangeBox().size(),
        deck.cardsInRedBox().size(),
        deck.session()
    );
  }
}
