package com.dann41.anki.core.deck.application.cardsolver;

import com.dann41.anki.core.deck.domain.Deck;
import com.dann41.anki.core.deck.domain.DeckFinder;
import com.dann41.anki.core.deck.domain.DeckId;
import com.dann41.anki.core.deck.domain.DeckRepository;
import com.dann41.anki.core.user.domain.UserId;

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
    Deck deck = deckFinder.execute(new DeckId(command.deckId()), new UserId(command.userId()));

    deck.solveCard(LocalDate.now(clock), command.cardId(), command.boxName());
    deckRepository.save(deck);
  }
}
