package com.dann41.anki.core.deck.domain;

import java.util.List;

public interface DeckRepository {

  Deck findById(DeckId deckId);

  List<Deck> findAll();

  void save(Deck deck);
}
