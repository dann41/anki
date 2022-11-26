package com.dann41.anki.core.cardcollection.domain;

import java.util.Collection;
import java.util.List;

public interface CardCollectionRepository {

  CardCollection findById(CardCollectionId id);

  Collection<CardCollection> findAll();

  void saveAll(List<CardCollection> collections);
}
