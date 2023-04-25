package com.dann41.anki.core.deck.application.cardpicker;

import com.dann41.anki.core.deck.cardpicker.CardPickerQuery;
import com.dann41.anki.core.deck.cardpicker.CardPickerResponse;
import com.dann41.anki.core.deck.domain.Deck;
import com.dann41.anki.core.deck.domain.DeckId;
import com.dann41.anki.core.deck.domain.DeckMother;
import com.dann41.anki.core.deck.domain.DeckNotFoundException;
import com.dann41.anki.core.deck.domain.DeckRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static com.dann41.anki.core.deck.domain.DeckMother.DECK_ID;
import static com.dann41.anki.core.deck.domain.DeckMother.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CardPickerTest {

  @Mock
  private DeckRepository deckRepository;

  private final Clock clock = Clock.fixed(Instant.parse("2022-11-10T22:30:00.00Z"), ZoneId.systemDefault());

  private CardPicker cardPicker;


  @BeforeEach
  public void setup() {
    cardPicker = new CardPicker(deckRepository, clock);
  }

  @Test
  public void givenExistingDeckWithUnansweredCardsShouldReturnNextCard() {
    givenDeck(DeckMother.defaultDeck());

    CardPickerResponse response = cardPicker.execute(cardPickerQuery());

    assertThat(response).isEqualTo(new CardPickerResponse("A", "A", "Answer A"));
  }

  @Test
  public void givenNonExistingDeckShouldThrowException() {
    assertThatThrownBy(() -> cardPicker.execute(cardPickerQuery()))
        .isInstanceOf(DeckNotFoundException.class);
  }

  @Test
  public void givenExistingDeckWithoutPendingCardsShouldReturnNull() {
    givenDeck(DeckMother.withoutPendingForToday());

    CardPickerResponse response = cardPicker.execute(cardPickerQuery());

    assertThat(response).isNull();
  }

  private void givenDeck(Deck deck) {
    given(deckRepository.findById(new DeckId(DECK_ID))).willReturn(deck);
  }

  private static CardPickerQuery cardPickerQuery() {
    return new CardPickerQuery(DECK_ID, USER_ID);
  }
}