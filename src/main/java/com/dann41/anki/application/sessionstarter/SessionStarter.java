package com.dann41.anki.application.sessionstarter;

import com.dann41.anki.domain.deck.Deck;
import com.dann41.anki.domain.deck.DeckId;
import com.dann41.anki.domain.deck.DeckNotFoundException;
import com.dann41.anki.domain.deck.DeckRepository;

import java.time.Clock;
import java.time.LocalDate;

public class SessionStarter {

  private final DeckRepository deckRepository;
  private final Clock clock;

  public SessionStarter(DeckRepository deckRepository, Clock clock) {
    this.deckRepository = deckRepository;
    this.clock = clock;
  }

  public void execute(StartSessionCommand command) {
    DeckId id = new DeckId(command.deckId());
    Deck deck = deckRepository.findById(id);
    if (deck == null) {
      throw new DeckNotFoundException(id);
    }

    deck.startNewSession(LocalDate.now(clock));
    deckRepository.save(deck);
  }
}
