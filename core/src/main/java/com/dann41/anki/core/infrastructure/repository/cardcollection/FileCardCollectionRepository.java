package com.dann41.anki.core.infrastructure.repository.cardcollection;

import com.dann41.anki.core.domain.card.CardDTO;
import com.dann41.anki.core.domain.cardcollection.CardCollection;
import com.dann41.anki.core.domain.cardcollection.CardCollectionId;
import com.dann41.anki.core.domain.cardcollection.CardCollectionRepository;
import com.dann41.anki.core.infrastructure.repository.card.FileCardImporter;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class FileCardCollectionRepository implements CardCollectionRepository {

  private final Map<String, CardCollectionDTO> cardCollections = new ConcurrentHashMap<>();

  public FileCardCollectionRepository(FileCardImporter fileCardImporter) {
    var cards = fileCardImporter.load();
    var collection = new CardCollection(
        "arts",
        "arts",
        "",
        cards.stream().map(card -> new CardDTO(card.question(), card.answer())).collect(Collectors.toList())
    );
    cardCollections.put(collection.id(), CardCollectionDTO.fromDomain(collection));
  }

  @Override
  public CardCollection findById(CardCollectionId id) {
    CardCollectionDTO collectionDTO = cardCollections.get(id.value());
    if (collectionDTO == null) {
      return null;
    }

    return collectionDTO.toDomain();
  }

  @Override
  public Collection<CardCollection> findAll() {
    return cardCollections.values().stream().map(CardCollectionDTO::toDomain).collect(Collectors.toList());
  }
}
