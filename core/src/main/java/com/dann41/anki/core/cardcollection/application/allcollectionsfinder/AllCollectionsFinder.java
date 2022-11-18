package com.dann41.anki.core.cardcollection.application.allcollectionsfinder;

import com.dann41.anki.core.cardcollection.domain.CardCollection;
import com.dann41.anki.core.cardcollection.domain.CardCollectionRepository;

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
