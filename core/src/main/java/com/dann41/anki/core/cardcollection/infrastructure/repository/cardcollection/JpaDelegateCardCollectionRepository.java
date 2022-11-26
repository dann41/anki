package com.dann41.anki.core.cardcollection.infrastructure.repository.cardcollection;

import com.dann41.anki.core.cardcollection.domain.CardCollection;
import com.dann41.anki.core.cardcollection.domain.CardCollectionId;
import com.dann41.anki.core.cardcollection.domain.CardCollectionRepository;
import com.dann41.anki.core.cardcollection.infrastructure.repository.cardcollection.jpa.CardCollectionDTO;
import com.dann41.anki.core.cardcollection.infrastructure.repository.cardcollection.jpa.CardCollectionEntity;
import com.dann41.anki.core.cardcollection.infrastructure.repository.cardcollection.jpa.CardCollectionJpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class JpaDelegateCardCollectionRepository implements CardCollectionRepository {
  private final CardCollectionJpaRepository jpaRepository;

  public JpaDelegateCardCollectionRepository(CardCollectionJpaRepository jpaRepository) {
    this.jpaRepository = jpaRepository;
  }

  @Override
  public CardCollection findById(CardCollectionId id) {
    return jpaRepository.findById(id.value())
        .map(CardCollectionEntity::payload)
        .map(CardCollectionDTO::toDomain)
        .orElse(null);
  }

  @Override
  public Collection<CardCollection> findAll() {
    return jpaRepository.findAll().stream()
        .map(CardCollectionEntity::payload)
        .map(CardCollectionDTO::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public void saveAll(List<CardCollection> collections) {
    if (collections == null) {
      return;
    }

    jpaRepository.saveAll(collections.stream()
        .map(this::toJpaEntity)
        .collect(Collectors.toList()));
  }

  private CardCollectionEntity toJpaEntity(CardCollection cardCollection) {
    return CardCollectionEntity.of(
        cardCollection.id(),
        CardCollectionDTO.fromDomain(cardCollection),
        0L
    );
  }
}
