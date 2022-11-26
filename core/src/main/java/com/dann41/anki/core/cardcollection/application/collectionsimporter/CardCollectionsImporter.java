package com.dann41.anki.core.cardcollection.application.collectionsimporter;

import com.dann41.anki.core.cardcollection.domain.CardCollection;
import com.dann41.anki.core.cardcollection.domain.CardCollectionRepository;
import com.dann41.anki.core.cardcollection.domain.card.CardDTO;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CardCollectionsImporter {

  private final CardCollectionRepository cardCollectionRepository;

  public CardCollectionsImporter(CardCollectionRepository cardCollectionRepository) {
    this.cardCollectionRepository = cardCollectionRepository;
  }

  public void execute(ImportCollectionsCommand command) {
    List<CardCollection> collections = toCollections(command);
    cardCollectionRepository.saveAll(collections);
  }

  private List<CardCollection> toCollections(ImportCollectionsCommand command) {
    if (command.collections() == null) {
      return Collections.emptyList();
    }

    return command.collections()
        .stream()
        .map(this::toCollection)
        .collect(Collectors.toList());
  }

  private CardCollection toCollection(CardCollectionRequest cardCollectionRequest) {
    return new CardCollection(
        cardCollectionRequest.id(),
        cardCollectionRequest.name(),
        "",
        cardCollectionRequest.cards().stream().map(this::toCardDto).collect(Collectors.toList())
    );
  }

  private CardDTO toCardDto(CardRequest cardRequest) {
    return new CardDTO(cardRequest.question(), cardRequest.answer());
  }
}
