package com.dann41.anki.api.infrastructure.controllers.collection.importer;

import com.dann41.anki.core.cardcollection.application.collectionsimporter.CardRequest;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.stream.Collectors;

public class FileCollectionReader {
  private static final String FIELD_SEPARATOR = "\\|";

  public Mono<Collection<CardRequest>> readCards(Flux<DataBuffer> dataBufferFlux) {
    return DataBufferUtils.join(dataBufferFlux)
            .map(buffer -> readCards(buffer.asInputStream()));
  }

  private Collection<CardRequest> readCards(InputStream inputStream) {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
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
