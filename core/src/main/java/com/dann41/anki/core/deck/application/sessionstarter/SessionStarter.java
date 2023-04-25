package com.dann41.anki.core.deck.application.sessionstarter;

import com.dann41.anki.core.deck.domain.Deck;
import com.dann41.anki.core.deck.domain.DeckFinder;
import com.dann41.anki.core.deck.domain.DeckId;
import com.dann41.anki.core.deck.domain.DeckRepository;
import com.dann41.anki.core.deck.sessionstarter.StartSessionCommand;
import com.dann41.anki.core.user.domain.UserId;

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
    Deck deck = deckFinder.execute(new DeckId(command.deckId()), new UserId(command.userId()));
    deck.startNewSession(LocalDate.now(clock));
    deckRepository.save(deck);
  }
}
