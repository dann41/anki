package com.dann41.anki.core.infrastructure.repository.card;


import com.dann41.anki.core.domain.card.Card;
import com.dann41.anki.core.domain.card.CardRepository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FileCardRepository implements CardRepository {

  private final Map<String, Card> cards = new ConcurrentHashMap<>();

  public FileCardRepository(FileCardImporter cardImportService) {
    cardImportService.load()
        .forEach(card -> cards.put(card.id(), card));
  }

  @Override
  public Card findCardById(String cardId) {
    return cards.get(cardId);
  }

  @Override
  public Collection<Card> getAll() {
    return cards.values();
  }
}
