package com.dann41.anki.core.application.collection.allcollectionsfinder;

import com.dann41.anki.core.domain.cardcollection.CardCollection;
import com.dann41.anki.core.domain.cardcollection.CardCollectionRepository;

import java.util.stream.Collectors;

public class AllCollectionsFinder {

  private final CardCollectionRepository cardCollectionRepository;

  public AllCollectionsFinder(CardCollectionRepository cardCollectionRepository) {
    this.cardCollectionRepository = cardCollectionRepository;
  }

  public CollectionsResponse execute() {
    return new CollectionsResponse(
        cardCollectionRepository.findAll()
            .stream()
            .map(this::toCollectionSummary)
            .collect(Collectors.toList())
    );
  }

  private CardCollectionSummary toCollectionSummary(CardCollection cardCollection) {
    return new CardCollectionSummary(
        cardCollection.id(),
        cardCollection.name(),
        cardCollection.description(),
        cardCollection.cards().size()
    );
  }
}
