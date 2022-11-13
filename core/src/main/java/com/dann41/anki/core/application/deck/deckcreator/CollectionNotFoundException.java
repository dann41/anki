package com.dann41.anki.core.application.deck.deckcreator;

import com.dann41.anki.core.domain.cardcollection.CardCollectionId;

public class CollectionNotFoundException extends RuntimeException {
  public CollectionNotFoundException(CardCollectionId id) {
    super("Collection not found for id " + id.value());
  }
}
