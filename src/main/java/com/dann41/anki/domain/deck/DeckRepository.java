package com.dann41.anki.domain.deck;

import java.util.List;

public interface DeckRepository {

  Deck findById(DeckId deckId);

  List<Deck> findAll();

  void save(Deck deck);
}
