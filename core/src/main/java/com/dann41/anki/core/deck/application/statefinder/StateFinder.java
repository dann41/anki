package com.dann41.anki.core.deck.application.statefinder;

import com.dann41.anki.core.deck.domain.Deck;
import com.dann41.anki.core.deck.domain.DeckFinder;
import com.dann41.anki.core.deck.domain.DeckId;
import com.dann41.anki.core.deck.domain.DeckRepository;

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
