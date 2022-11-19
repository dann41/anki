package com.dann41.anki.core.deck.application.alldecksfinder;

import com.dann41.anki.core.deck.domain.Deck;
import com.dann41.anki.core.deck.domain.DeckRepository;
import com.dann41.anki.core.user.domain.UserId;

import java.util.stream.Collectors;

public class MyDecksFinder {

  private final DeckRepository deckRepository;

  public MyDecksFinder(DeckRepository deckRepository) {
    this.deckRepository = deckRepository;
  }

  public DecksResponse execute(MyDecksFinderQuery query) {
    return new DecksResponse(
        deckRepository.findByUserId(new UserId(query.userId()))
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
