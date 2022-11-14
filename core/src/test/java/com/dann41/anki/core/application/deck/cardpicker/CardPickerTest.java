package com.dann41.anki.core.application.deck.cardpicker;

import com.dann41.anki.core.domain.DeckMother;
import com.dann41.anki.core.domain.deck.Deck;
import com.dann41.anki.core.domain.deck.DeckId;
import com.dann41.anki.core.domain.deck.DeckNotFoundException;
import com.dann41.anki.core.domain.deck.DeckRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static com.dann41.anki.core.domain.DeckMother.DECK_ID;
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

    CardResponse response = cardPicker.execute(DECK_ID);

    assertThat(response).isEqualTo(new CardResponse("A", "A", "Answer A"));
  }

  @Test
  public void givenNonExistingDeckShouldThrowException() {
    assertThatThrownBy(() -> cardPicker.execute(DECK_ID))
        .isInstanceOf(DeckNotFoundException.class);
  }

  @Test
  public void givenExistingDeckWithoutPendingCardsShouldReturnNull() {
    givenDeck(DeckMother.withoutPendingForToday());

    CardResponse response = cardPicker.execute(DECK_ID);

    assertThat(response).isNull();
  }

  private void givenDeck(Deck deck) {
    given(deckRepository.findById(new DeckId(DECK_ID))).willReturn(deck);
  }
}