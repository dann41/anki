package com.dann41.anki.application.deckcreator;

import com.dann41.anki.domain.card.Card;
import com.dann41.anki.domain.card.CardRepository;
import com.dann41.anki.domain.deck.Deck;
import com.dann41.anki.domain.deck.DeckAlreadyExistsException;
import com.dann41.anki.domain.deck.DeckId;
import com.dann41.anki.domain.deck.DeckRepository;

import java.util.List;
import java.util.stream.Collectors;

public class DeckCreator {
  private final DeckRepository deckRepository;
  private final CardRepository cardRepository;

  public DeckCreator(DeckRepository deckRepository, CardRepository cardRepository) {
    this.deckRepository = deckRepository;
    this.cardRepository = cardRepository;
  }

  public void execute(CreateDeckCommand command) {
    DeckId id = new DeckId(command.deckId());
    Deck existingDeck = deckRepository.findById(id);
    if (existingDeck != null) {
      throw new DeckAlreadyExistsException(id);
    }

    Deck newDeck = Deck.create(command.deckId(), loadCards());
    deckRepository.save(newDeck);
  }

  private List<String> loadCards() {
    return cardRepository.getAll().stream().map(Card::question).collect(Collectors.toList());
  }
}
