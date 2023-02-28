package com.dann41.anki.cmd.intrastructure.services;

import com.dann41.anki.core.cardcollection.application.collectionsimporter.CardCollectionRequest;
import com.dann41.anki.core.cardcollection.application.collectionsimporter.CardRequest;
import com.dann41.anki.core.cardcollection.application.collectionsimporter.CardCollectionsImporter;
import com.dann41.anki.core.cardcollection.application.collectionsimporter.ImportCollectionsCommand;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class FileCollectionImporter {
  private static final String FIELD_SEPARATOR = "\\|";

  private final CardCollectionsImporter cardCollectionsImporter;

  public FileCollectionImporter(CardCollectionsImporter cardCollectionsImporter) {
    this.cardCollectionsImporter = cardCollectionsImporter;
  }

  public void importCollection(String resourceName, String id, String name) {
    var cards = readCards(resourceName);
    var collection = new CardCollectionRequest(id, name, cards);
    cardCollectionsImporter.execute(new ImportCollectionsCommand(List.of(collection)));
  }

  private Collection<CardRequest> readCards(String resourceName) {
    try (BufferedReader reader = new BufferedReader(new FileReader(resourceName))) {
      return reader.lines()
          .skip(1)
          .map(this::lineToCard)
          .filter(this::isValidCard)
          .collect(Collectors.toList());
    } catch (IOException e) {
      throw new RuntimeException("Cannot load cards", e);
    }
  }

  private CardRequest lineToCard(String s) {
    String[] parts = s.split(FIELD_SEPARATOR);
    if (parts.length == 2) {
      return new CardRequest(parts[0], parts[1]);
    }
    return null;
  }

  private boolean isValidCard(CardRequest card) {
    return card != null &&
        card.question() != null && !card.question().isEmpty() &&
        card.answer() != null && !card.answer().isEmpty();
  }
}
