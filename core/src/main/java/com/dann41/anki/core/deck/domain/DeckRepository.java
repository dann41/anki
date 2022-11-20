package com.dann41.anki.core.deck.domain;

import com.dann41.anki.core.user.domain.UserId;

import java.util.List;

public interface DeckRepository {

  Deck findById(DeckId deckId);

  List<Deck> findByUserId(UserId userId);

  List<Deck> findAll();

  void save(Deck deck);
}
