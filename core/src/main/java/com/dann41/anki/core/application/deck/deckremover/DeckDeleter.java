package com.dann41.anki.core.application.deck.deckremover;

import com.dann41.anki.core.domain.deck.Deck;
import com.dann41.anki.core.domain.deck.DeckFinder;
import com.dann41.anki.core.domain.deck.DeckId;
import com.dann41.anki.core.domain.deck.DeckRepository;

public class DeckDeleter {
  private final DeckFinder deckFinder;
  private final DeckRepository deckRepository;

  public DeckDeleter(DeckRepository deckRepository) {
    this.deckRepository = deckRepository;
    this.deckFinder = new DeckFinder(deckRepository);
  }

  public void execute(DeleteDeckCommand command) {
    Deck deck = deckFinder.execute(new DeckId(command.deckId()));

    deck.delete();

    deckRepository.save(deck);
  }
}
