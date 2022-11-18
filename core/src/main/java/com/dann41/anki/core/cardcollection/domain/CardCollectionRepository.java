package com.dann41.anki.core.cardcollection.domain;

import java.util.Collection;

public interface CardCollectionRepository {

  CardCollection findById(CardCollectionId id);

  Collection<CardCollection> findAll();
}
