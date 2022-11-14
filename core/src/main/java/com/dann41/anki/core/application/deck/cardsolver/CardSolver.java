package com.dann41.anki.core.application.deck.cardsolver;

import com.dann41.anki.core.domain.deck.Deck;
import com.dann41.anki.core.domain.deck.DeckFinder;
import com.dann41.anki.core.domain.deck.DeckId;
import com.dann41.anki.core.domain.deck.DeckRepository;

import java.time.Clock;
import java.time.LocalDate;

public class CardSolver {
  private final DeckRepository deckRepository;
  private final DeckFinder deckFinder;
  private final Clock clock;

  public CardSolver(DeckRepository deckRepository, Clock clock) {
    this.deckRepository = deckRepository;
    this.deckFinder = new DeckFinder(deckRepository);
    this.clock = clock;
  }

  public void execute(SolveCardCommand command) {
    Deck deck = deckFinder.execute(new DeckId(command.deckId()));

    deck.solveCard(LocalDate.now(clock), command.cardId(), command.boxName());
    deckRepository.save(deck);
  }
}
