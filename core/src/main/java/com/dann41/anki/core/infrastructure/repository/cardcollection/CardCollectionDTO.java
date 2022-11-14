package com.dann41.anki.core.infrastructure.repository.cardcollection;

import com.dann41.anki.core.domain.card.CardDTO;
import com.dann41.anki.core.domain.cardcollection.CardCollection;

import java.util.Collection;
import java.util.stream.Collectors;

public record CardCollectionDTO(String id, String name, String description, Collection<CardDTO> cards) {

  public CardCollection toDomain() {
    return new CardCollection(
        id(),
        name(),
        description(),
        cards().stream().toList()
    );
  }

  public static CardCollectionDTO fromDomain(CardCollection cardCollection) {
    return new CardCollectionDTO(
        cardCollection.id(),
        cardCollection.name(),
        cardCollection.description(),
        cardCollection.cards()
    );
  }
}
