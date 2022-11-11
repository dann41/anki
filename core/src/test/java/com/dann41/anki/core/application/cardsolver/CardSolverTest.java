package com.dann41.anki.core.application.cardsolver;

import com.dann41.anki.core.domain.DeckMother;
import com.dann41.anki.core.domain.deck.Deck;
import com.dann41.anki.core.domain.deck.DeckId;
import com.dann41.anki.core.domain.deck.DeckNotFoundException;
import com.dann41.anki.core.domain.deck.DeckRepository;
import com.dann41.anki.core.domain.deck.SessionNotStartedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import static com.dann41.anki.core.domain.DeckMother.DECK_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CardSolverTest {

  private static final String NEXT_CARD_ID = "A";
  private static final String ANOTHER_CARD_ID = "B";

  @Mock
  private DeckRepository deckRepository;

  private final Clock clock = Clock.fixed(Instant.parse("2022-11-10T22:30:00.00Z"), ZoneId.systemDefault());

  private CardSolver cardSolver;

  @BeforeEach
  public void setup() {
    cardSolver = new CardSolver(deckRepository, clock);
  }

  @Test
  public void shouldPutNextCardInGreenBox() {
    givenExistingDeck();
    SolveCardCommand command = new SolveCardCommand(DECK_ID, NEXT_CARD_ID, "green");

    cardSolver.execute(command);

    verifyDeckWithCardInBox();
  }

  @Test
  public void shouldPutNextCardInGreenBoxOnDeckWithoutUnplayedCards() {
    givenExistingDeckWithoutUnplayedCards();
    SolveCardCommand command = new SolveCardCommand(DECK_ID, NEXT_CARD_ID, "green");

    cardSolver.execute(command);

    verifyDeckWithCardInBox();
  }

  @Test
  public void shouldFailWhenSolvingDifferentCardThanNextOne() {
    givenExistingDeck();
    SolveCardCommand command = new SolveCardCommand(DECK_ID, ANOTHER_CARD_ID, "green");

    assertThatThrownBy(() -> cardSolver.execute(command))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void shouldFailWithDeckWithInSession() {
    givenExistingDeckWithOldSession();
    SolveCardCommand command = new SolveCardCommand(DECK_ID, ANOTHER_CARD_ID, "green");

    assertThatThrownBy(() -> cardSolver.execute(command))
        .isInstanceOf(SessionNotStartedException.class);
  }

  @Test
  public void shouldFailWhenDeckDoesNotExist() {
    SolveCardCommand command = new SolveCardCommand(DECK_ID, ANOTHER_CARD_ID, "green");

    assertThatThrownBy(() -> cardSolver.execute(command))
        .isInstanceOf(DeckNotFoundException.class);
  }

  @Test
  public void shouldFailWhenInvalidBoxName() {
    givenExistingDeck();
    SolveCardCommand command = new SolveCardCommand(DECK_ID, NEXT_CARD_ID, "patata");

    assertThatThrownBy(() -> cardSolver.execute(command))
        .isInstanceOf(IllegalArgumentException.class)
        .message().isEqualTo("Unknown box patata");
  }

  private void givenExistingDeck() {
    given(deckRepository.findById(new DeckId(DECK_ID)))
        .willReturn(DeckMother.defaultDeck());
  }

  private void givenExistingDeckWithoutUnplayedCards() {
    given(deckRepository.findById(new DeckId(DECK_ID)))
        .willReturn(DeckMother.withoutUnplayed());
  }

  private void givenExistingDeckWithOldSession() {
    given(deckRepository.findById(new DeckId(DECK_ID)))
        .willReturn(DeckMother.withSession(LocalDate.of(2022, 11, 5)));
  }

  private void verifyDeckWithCardInBox() {
    ArgumentCaptor<Deck> captor = ArgumentCaptor.forClass(Deck.class);
    verify(deckRepository, times(1)).save(captor.capture());

    Deck savedDeck = captor.getValue();
    assertThat(savedDeck.unplayedCards()).doesNotContain("A");
    assertThat(savedDeck.cardsInGreenBox()).last().isEqualTo("A");
  }

}
