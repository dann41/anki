package com.dann41.anki.core.deck.application.statefinder;

import com.dann41.anki.core.deck.domain.Deck;
import com.dann41.anki.core.deck.domain.DeckFinder;
import com.dann41.anki.core.deck.domain.DeckId;
import com.dann41.anki.core.deck.domain.DeckRepository;
import com.dann41.anki.core.deck.statefinder.FindStatusQuery;
import com.dann41.anki.core.deck.statefinder.FindStatusResponse;
import com.dann41.anki.core.user.domain.UserId;

public class StatusFinder {
  private final DeckFinder deckFinder;

  public StatusFinder(DeckRepository deckRepository) {
    this.deckFinder = new DeckFinder(deckRepository);
  }

  public FindStatusResponse execute(FindStatusQuery query) {
    Deck deck = deckFinder.execute(new DeckId(query.deckId()), new UserId(query.userId()));
    return new FindStatusResponse(
        deck.unansweredCards().size(),
        deck.cardsInGreenBox().size(),
        deck.cardsInOrangeBox().size(),
        deck.cardsInRedBox().size(),
        deck.session()
    );
  }
}
