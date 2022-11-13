package com.dann41.anki.core.application.deck.cardpicker;

import com.dann41.anki.core.domain.card.Card;
import com.dann41.anki.core.domain.card.CardRepository;
import com.dann41.anki.core.domain.deck.Deck;
import com.dann41.anki.core.domain.deck.DeckId;
import com.dann41.anki.core.domain.deck.DeckNotFoundException;
import com.dann41.anki.core.domain.card.CardNotFoundException;
import com.dann41.anki.core.domain.deck.DeckRepository;

import java.time.Clock;
import java.time.LocalDate;

public class CardPicker {

  private final DeckRepository deckRepository;
  private final CardRepository cardRepository;
  private final Clock clock;

  public CardPicker(DeckRepository deckRepository, CardRepository cardRepository, Clock clock) {
    this.deckRepository = deckRepository;
    this.cardRepository = cardRepository;
    this.clock = clock;
  }

  public CardResponse execute(String deckId) {
    DeckId id = new DeckId(deckId);
    Deck deck = deckRepository.findById(id);
    if (deck == null) {
      throw new DeckNotFoundException(id);
    }

    String cardId = deck.pickNextCardId(LocalDate.now(clock));
    if (cardId == null) {
      return null;
    }

    Card card = cardRepository.findCardById(cardId);
    if (card == null) {
      throw new CardNotFoundException(cardId);
    }

    return new CardResponse(
        cardId,
        card.question(),
        card.answer()
    );
  }
}
