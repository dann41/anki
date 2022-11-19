package com.dann41.anki.core.deck.application.deckcreator;

import com.dann41.anki.core.cardcollection.domain.CardCollection;
import com.dann41.anki.core.cardcollection.domain.CardCollectionId;
import com.dann41.anki.core.cardcollection.domain.CardCollectionRepository;
import com.dann41.anki.core.deck.domain.Deck;
import com.dann41.anki.core.deck.domain.DeckAlreadyExistsException;
import com.dann41.anki.core.deck.domain.DeckId;
import com.dann41.anki.core.deck.domain.DeckRepository;
import com.dann41.anki.core.deck.domain.Question;

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

    Deck newDeck = Deck.create(command.deckId(), command.userId(), loadCards(command.collectionId()));
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
