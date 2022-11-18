package com.dann41.anki.core.deck.application.deckcreator;

import com.dann41.anki.core.cardcollection.domain.CardCollectionId;

public class CollectionNotFoundException extends RuntimeException {
  public CollectionNotFoundException(CardCollectionId id) {
    super("Collection not found for id " + id.value());
  }
}
