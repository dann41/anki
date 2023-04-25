package com.dann41.anki.core.deck.application.cardpicker;

import com.dann41.anki.core.deck.domain.*;
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

    public CardPickerResponse execute(CardPickerQuery query) {
        Deck deck = deckFinder.execute(new DeckId(query.deckId()), new UserId(query.userId()));
        Question question = deck.pickNextQuestion(LocalDate.now(clock));
        if (question == null) {
            return null;
        }

        return new CardPickerResponse(
                question.id(),
                question.question(),
                question.answer()
        );
    }
}
