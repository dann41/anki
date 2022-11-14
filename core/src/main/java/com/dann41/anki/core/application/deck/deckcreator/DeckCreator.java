package com.dann41.anki.core.application.deck.deckcreator;

import com.dann41.anki.core.domain.cardcollection.CardCollection;
import com.dann41.anki.core.domain.cardcollection.CardCollectionId;
import com.dann41.anki.core.domain.cardcollection.CardCollectionRepository;
import com.dann41.anki.core.domain.deck.Deck;
import com.dann41.anki.core.domain.deck.DeckAlreadyExistsException;
import com.dann41.anki.core.domain.deck.DeckId;
import com.dann41.anki.core.domain.deck.DeckRepository;
import com.dann41.anki.core.domain.deck.Question;

import java.util.List;
import java.util.stream.Collectors;

public class DeckCreator {
  private final DeckRepository deckRepository;
  private final CardCollectionRepository collectionRepository;

  public DeckCreator(DeckRepository deckRepository, CardCollectionRepository collectionRepository) {
    this.deckRepository = deckRepository;
    this.collectionRepository = collectionRepository;
  }

  public void execute(CreateDeckCommand command) {
    DeckId id = new DeckId(command.deckId());
    Deck existingDeck = deckRepository.findById(id);
    if (existingDeck != null) {
      throw new DeckAlreadyExistsException(id);
    }

    Deck newDeck = Deck.create(command.deckId(), loadCards(command.collectionId()));
    deckRepository.save(newDeck);
  }

  private List<Question> loadCards(String collectionId) {
    CardCollectionId id = new CardCollectionId(collectionId);
    CardCollection collection = collectionRepository.findById(id);
    if (collection == null) {
      throw new CollectionNotFoundException(id);
    }
    return collection.cards().stream()
        .map(card -> new Question(card.question(), card.answer()))
        .collect(Collectors.toList());
  }

}
