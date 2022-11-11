package com.dann41.anki.core.application.cardsolver;

import com.dann41.anki.core.domain.deck.Deck;
import com.dann41.anki.core.domain.deck.DeckNotFoundException;
import com.dann41.anki.core.domain.deck.DeckId;
import com.dann41.anki.core.domain.deck.DeckRepository;

import java.time.Clock;
import java.time.LocalDate;

public class CardSolver {
  private final DeckRepository deckRepository;
  private final Clock clock;

  public CardSolver(DeckRepository deckRepository, Clock clock) {
    this.deckRepository = deckRepository;
    this.clock = clock;
  }

  public void execute(SolveCardCommand command) {
    DeckId id = new DeckId(command.deckId());
    Deck deck = deckRepository.findById(id);
    if (deck == null) {
      throw new DeckNotFoundException(id);
    }

    deck.solveCard(LocalDate.now(clock), command.cardId(), command.boxName());
    deckRepository.save(deck);
  }
}
