package com.dann41.anki.core.cardcollection.infrastructure.repository.cardcollection.jpa;

import com.dann41.anki.core.cardcollection.domain.CardCollection;
import com.dann41.anki.core.cardcollection.domain.card.CardDTO;

import java.io.Serializable;
import java.util.Collection;

public record CardCollectionDTO(String id, String name, String description, Collection<Card> cards)
    implements Serializable {

  public CardCollection toDomain() {
    return new CardCollection(
        id(),
        name(),
        description(),
        cards().stream().map(c -> new CardDTO(c.question(), c.answer())).toList()
    );
  }

  public static CardCollectionDTO fromDomain(CardCollection cardCollection) {
    return new CardCollectionDTO(
        cardCollection.id(),
        cardCollection.name(),
        cardCollection.description(),
        cardCollection.cards().stream().map(c -> new Card(c.question(), c.answer())).toList()
    );
  }
}
