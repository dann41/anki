package com.dann41.anki.core.application.deck.cardpicker;

import com.dann41.anki.core.domain.deck.Deck;
import com.dann41.anki.core.domain.deck.DeckId;
import com.dann41.anki.core.domain.deck.DeckNotFoundException;
import com.dann41.anki.core.domain.deck.DeckRepository;
import com.dann41.anki.core.domain.deck.Question;

import java.time.Clock;
import java.time.LocalDate;

public class CardPicker {

  private final DeckRepository deckRepository;
  private final Clock clock;

  public CardPicker(DeckRepository deckRepository, Clock clock) {
    this.deckRepository = deckRepository;
    this.clock = clock;
  }

  public CardResponse execute(String deckId) {
    DeckId id = new DeckId(deckId);
    Deck deck = deckRepository.findById(id);
    if (deck == null) {
      throw new DeckNotFoundException(id);
    }

    Question question = deck.pickNextQuestion(LocalDate.now(clock));
    if (question == null) {
      return null;
    }

    return new CardResponse(
        question.id(),
        question.question(),
        question.answer()
    );
  }
}
