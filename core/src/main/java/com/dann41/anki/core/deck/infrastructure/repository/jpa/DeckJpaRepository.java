package com.dann41.anki.core.deck.infrastructure.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeckJpaRepository extends JpaRepository<DeckEntity, String> {
  List<DeckEntity> findByUserId(String value);
}
