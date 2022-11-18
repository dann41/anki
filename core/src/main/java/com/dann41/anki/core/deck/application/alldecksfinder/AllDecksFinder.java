package com.dann41.anki.core.deck.application.alldecksfinder;

import com.dann41.anki.core.deck.domain.Deck;
import com.dann41.anki.core.deck.domain.DeckRepository;

import java.util.stream.Collectors;

public class AllDecksFinder {

  private final DeckRepository deckRepository;

  public AllDecksFinder(DeckRepository deckRepository) {
    this.deckRepository = deckRepository;
  }

  public DecksResponse execute() {
    return new DecksResponse(
        deckRepository.findAll()
            .stream()
            .map(this::toDeckSummary)
            .collect(Collectors.toList())
    );
  }

  private DeckSummary toDeckSummary(Deck deck) {
    return new DeckSummary(
        deck.id().value(),
        deck.questions().size(),
        deck.session()
    );
  }
}
