package com.dann41.anki.core.cardcollection.infrastructure.repository.cardcollection;

import com.dann41.anki.core.cardcollection.domain.card.CardDTO;
import com.dann41.anki.core.cardcollection.domain.CardCollection;

import java.util.Collection;

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
