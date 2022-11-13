package com.dann41.anki.core.application.deck.statefinder;

import com.dann41.anki.core.domain.deck.Deck;
import com.dann41.anki.core.domain.deck.DeckFinder;
import com.dann41.anki.core.domain.deck.DeckId;
import com.dann41.anki.core.domain.deck.DeckRepository;

public class StateFinder {
  private final DeckFinder deckFinder;

  public StateFinder(DeckRepository deckRepository) {
    this.deckFinder = new DeckFinder(deckRepository);
  }

  public StateResponse execute(String deckId) {
    Deck deck = deckFinder.execute(new DeckId(deckId));
    return new StateResponse(
        deck.unansweredCards().size(),
        deck.cardsInGreenBox().size(),
        deck.cardsInOrangeBox().size(),
        deck.cardsInRedBox().size(),
        deck.session()
    );
  }
}
