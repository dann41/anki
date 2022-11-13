package com.dann41.anki.core.application.deck.sessionstarter;

import com.dann41.anki.core.domain.deck.Deck;
import com.dann41.anki.core.domain.deck.DeckFinder;
import com.dann41.anki.core.domain.deck.DeckId;
import com.dann41.anki.core.domain.deck.DeckNotFoundException;
import com.dann41.anki.core.domain.deck.DeckRepository;

import java.time.Clock;
import java.time.LocalDate;

public class SessionStarter {

  private final DeckRepository deckRepository;
  private final DeckFinder deckFinder;
  private final Clock clock;

  public SessionStarter(DeckRepository deckRepository, Clock clock) {
    this.deckRepository = deckRepository;
    this.deckFinder = new DeckFinder(deckRepository);
    this.clock = clock;
  }

  public void execute(StartSessionCommand command) {
    Deck deck = deckFinder.execute(new DeckId(command.deckId()));
    deck.startNewSession(LocalDate.now(clock));
    deckRepository.save(deck);
  }
}
