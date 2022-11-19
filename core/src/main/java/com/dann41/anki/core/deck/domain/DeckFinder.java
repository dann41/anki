package com.dann41.anki.core.deck.domain;

import com.dann41.anki.core.user.domain.UserId;

import java.util.Objects;

public class DeckFinder {

  private final DeckRepository deckRepository;

  public DeckFinder(DeckRepository deckRepository) {
    this.deckRepository = deckRepository;
  }

  public Deck execute(DeckId deckId, UserId userId) {
    Deck deck = deckRepository.findById(deckId);
    if (deck == null) {
      throw new DeckNotFoundException(deckId);
    }

    if (!Objects.equals(deck.userId(), userId)) {
      // Hide forbidden access
      throw new DeckNotFoundException(deckId);
    }

    return deck;
  }

}
