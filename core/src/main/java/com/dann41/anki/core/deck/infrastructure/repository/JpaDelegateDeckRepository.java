package com.dann41.anki.core.deck.infrastructure.repository;

import com.dann41.anki.core.deck.domain.Deck;
import com.dann41.anki.core.deck.domain.DeckId;
import com.dann41.anki.core.deck.domain.DeckRepository;
import com.dann41.anki.core.deck.infrastructure.repository.jpa.DeckEntity;
import com.dann41.anki.core.deck.infrastructure.repository.jpa.DeckJpaRepository;
import com.dann41.anki.core.user.domain.UserId;

import java.util.List;
import java.util.stream.Collectors;

public class JpaDelegateDeckRepository implements DeckRepository {

  private final DeckJpaRepository deckJpaRepository;

  public JpaDelegateDeckRepository(DeckJpaRepository deckJpaRepository) {
    this.deckJpaRepository = deckJpaRepository;
  }

  @Override
  public Deck findById(DeckId deckId) {
    return deckJpaRepository.findById(deckId.value())
        .map(DeckEntity::toDomain)
        .orElse(null);
  }

  @Override
  public List<Deck> findByUserId(UserId userId) {
    return deckJpaRepository.findByUserId(userId.value())
        .stream().map(DeckEntity::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public List<Deck> findAll() {
    return deckJpaRepository.findAll()
        .stream().map(DeckEntity::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public void save(Deck deck) {
    deckJpaRepository.save(DeckEntity.of(deck));
  }
}
