package com.dann41.anki.core.deck.application.cardpicker;

import com.dann41.anki.core.deck.domain.Deck;
import com.dann41.anki.core.deck.domain.DeckFinder;
import com.dann41.anki.core.deck.domain.DeckId;
import com.dann41.anki.core.deck.domain.DeckRepository;
import com.dann41.anki.core.deck.domain.Question;
import com.dann41.anki.core.user.domain.UserId;

import java.time.Clock;
import java.time.LocalDate;

public class CardPicker {

  private final DeckFinder deckFinder;
  private final Clock clock;

  public CardPicker(DeckRepository deckRepository, Clock clock) {
    this.deckFinder = new DeckFinder(deckRepository);
    this.clock = clock;
  }

  public CardResponse execute(CardPickerQuery query) {
      Deck deck = deckFinder.execute(new DeckId(query.deckId()), new UserId(query.userId()));
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
