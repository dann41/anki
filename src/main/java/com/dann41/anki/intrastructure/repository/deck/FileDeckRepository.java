package com.dann41.anki.intrastructure.repository.deck;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.dann41.anki.domain.deck.Deck;
import com.dann41.anki.domain.deck.DeckId;
import com.dann41.anki.domain.deck.DeckRepository;

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
    DeckDTO dto = toDTO(deck);
    deckMap.put(deck.id().value(), dto);

    saveIntoDisk();
  }

  private Map<String, DeckDTO> loadFromDisk() {
    try (FileReader fileOutputStream = new FileReader(fileName)) {
      return objectMapper.readValue(fileOutputStream, new TypeReference<Map<String, DeckDTO>>() {});
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

    return Deck.restore(
        storedDeck.id(),
        storedDeck.unplayedCards(),
        storedDeck.cardsInRedBox(),
        storedDeck.cardsInOrangeBox(),
        storedDeck.cardsInGreenBox(),
        storedDeck.session()
    );
  }

  private static DeckDTO toDTO(Deck deck) {
    return new DeckDTO(
        deck.id().value(),
        deck.unplayedCards(),
        deck.cardsInRedBox(),
        deck.cardsInOrangeBox(),
        deck.cardsInGreenBox(),
        deck.session()
    );
  }
}
