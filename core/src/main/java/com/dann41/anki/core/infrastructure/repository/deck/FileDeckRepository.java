package com.dann41.anki.core.infrastructure.repository.deck;

import com.dann41.anki.core.domain.deck.Deck;
import com.dann41.anki.core.domain.deck.DeckId;
import com.dann41.anki.core.domain.deck.DeckRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileDeckRepository implements DeckRepository {

  private final String fileName = "anki.data";
  private final ObjectMapper objectMapper;
  private final Map<String, DeckDTO> deckMap;

  public FileDeckRepository(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
    deckMap = loadFromDisk();
  }

  @Override
  public Deck findById(DeckId deckId) {
    DeckDTO storedDeck = deckMap.get(deckId.value());
    return fromDTO(storedDeck);
  }

  @Override
  public List<Deck> findAll() {
    return deckMap.values().stream()
        .sorted(Comparator.comparing(DeckDTO::id))
        .map(FileDeckRepository::fromDTO)
        .collect(Collectors.toList());
  }

  @Override
  public void save(Deck deck) {
    if (deck.isDeleted()) {
     delete(deck.id());
     return;
    }

    deckMap.put(deck.id().value(), DeckDTO.fromDeck(deck));
    saveIntoDisk();
  }

  private void delete(DeckId id) {
    deckMap.remove(id.value());
    saveIntoDisk();
  }

  private Map<String, DeckDTO> loadFromDisk() {
    try (FileReader fileOutputStream = new FileReader(fileName)) {
      return objectMapper.readValue(fileOutputStream, new TypeReference<>() {});
    } catch (IOException e) {
      return new HashMap<>();
    }
  }

  private void saveIntoDisk() {
    try (FileWriter fileWriter = new FileWriter(fileName)) {
      objectMapper.writeValue(fileWriter, deckMap);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static Deck fromDTO(DeckDTO storedDeck) {
    if (storedDeck == null) {
      return null;
    }

    return storedDeck.toDeck();
  }
}
