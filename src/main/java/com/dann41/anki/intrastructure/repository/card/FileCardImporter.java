package com.dann41.anki.intrastructure.repository.card;

import com.dann41.anki.domain.card.Card;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

public class FileCardImporter {

  private static final String FIELD_SEPARATOR = "\\|";
  private static final String FILENAME = "src/main/resources/cards.tsv";

  private final String resourceName;

  public FileCardImporter(String resourceName) {
    this.resourceName = resourceName;
  }

  public FileCardImporter() {
    this(FILENAME);
  }

  public Collection<Card> load() {
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

  private Card lineToCard(String s) {
    String[] parts = s.split(FIELD_SEPARATOR);
    if (parts.length == 2) {
      return new Card(parts[0], parts[1]);
    }
    return null;
  }

  private boolean isValidCard(Card card) {
    return card != null &&
        card.question() != null && !card.question().isEmpty() &&
        card.answer() != null && !card.answer().isEmpty();
  }
}
