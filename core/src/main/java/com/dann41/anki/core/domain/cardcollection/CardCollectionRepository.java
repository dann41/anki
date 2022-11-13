package com.dann41.anki.core.domain.cardcollection;

public interface CardCollectionRepository {

  CardCollection findById(CardCollectionId id);

}
