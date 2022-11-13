package com.dann41.anki.core.application.sessionstarter;

import com.dann41.anki.core.application.deck.sessionstarter.SessionStarter;
import com.dann41.anki.core.application.deck.sessionstarter.StartSessionCommand;
import com.dann41.anki.core.domain.DeckMother;
import com.dann41.anki.core.domain.deck.Deck;
import com.dann41.anki.core.domain.deck.DeckId;
import com.dann41.anki.core.domain.deck.DeckRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SessionStarterTest {

  private static final DeckId DECK_ID = new DeckId("1234");

  @Mock
  private DeckRepository deckRepository;

  private final Clock clock = Clock.fixed(Instant.parse("2022-11-11T22:30:00.00Z"), ZoneId.systemDefault());

  private SessionStarter sessionStarter;

  @BeforeEach
  public void setup() {
    sessionStarter = new SessionStarter(deckRepository, clock);
  }

  @Test
  public void shouldStartFirstSession() {
    givenExistingDeck();
    StartSessionCommand command = new StartSessionCommand(DECK_ID.value());

    sessionStarter.execute(command);

    verifyDeckSavedWithSessionStarted();
  }

  @Test
  public void shouldStartNewSession() {
    givenExistingDeckWithYesterdaySession();
    StartSessionCommand command = new StartSessionCommand(DECK_ID.value());

    sessionStarter.execute(command);

    verifyDeckSavedWithSessionStarted();
  }

  @Test
  public void shouldRotateBoxesOnce() {
    givenExistingDeckWithYesterdaySession();
    StartSessionCommand command = new StartSessionCommand(DECK_ID.value());

    sessionStarter.execute(command);

    verifyDeckSavedWithBoxRotatedOnce();
  }

  @Test
  public void shouldRotateBoxesTwice() {
    givenExistingDeckWithOldSession();
    StartSessionCommand command = new StartSessionCommand(DECK_ID.value());

    sessionStarter.execute(command);

    verifyDeckSavedWithBoxRotatedTwice();
  }

  @Test
  public void shouldNotRotateBoxesWhenStartSessionOnSameDay() {
    givenExistingDeckWithTodaySession();
    StartSessionCommand command = new StartSessionCommand(DECK_ID.value());

    sessionStarter.execute(command);

    verifyDeckSavedWithoutRotatingBoxes();
  }

  private void givenExistingDeck() {
    given(deckRepository.findById(DECK_ID))
        .willReturn(DeckMother.withSession(null));
  }

  private void givenExistingDeckWithYesterdaySession() {
    LocalDate lastSession = LocalDate.of(2022, 11, 10);
    given(deckRepository.findById(DECK_ID))
        .willReturn(DeckMother.withSession(lastSession));
  }

  private void givenExistingDeckWithOldSession() {
    LocalDate lastSession = LocalDate.of(2022, 11, 5);
    given(deckRepository.findById(DECK_ID))
        .willReturn(DeckMother.withSession(lastSession));
  }

  private void givenExistingDeckWithTodaySession() {
    LocalDate lastSession = LocalDate.of(2022, 11, 11);
    given(deckRepository.findById(DECK_ID))
        .willReturn(DeckMother.withSession(lastSession));
  }

  private void verifyDeckSavedWithSessionStarted() {
    ArgumentCaptor<Deck> captor = ArgumentCaptor.forClass(Deck.class);
    verify(deckRepository, times(1)).save(captor.capture());
    Deck savedDeck = captor.getValue();
    assertThat(savedDeck.session()).isEqualTo(LocalDate.of(2022, 11, 11));
  }

  private void verifyDeckSavedWithoutRotatingBoxes() {
    ArgumentCaptor<Deck> captor = ArgumentCaptor.forClass(Deck.class);
    verify(deckRepository, times(1)).save(captor.capture());
    Deck savedDeck = captor.getValue();
    assertThat(savedDeck.unplayedCards()).containsExactly("A");
    assertThat(savedDeck.cardsInRedBox()).containsExactly("B");
    assertThat(savedDeck.cardsInOrangeBox()).containsExactly("C");
    assertThat(savedDeck.cardsInGreenBox()).containsExactly("D");
  }

  private void verifyDeckSavedWithBoxRotatedOnce() {
    ArgumentCaptor<Deck> captor = ArgumentCaptor.forClass(Deck.class);
    verify(deckRepository, times(1)).save(captor.capture());
    Deck savedDeck = captor.getValue();
    assertThat(savedDeck.unplayedCards()).containsExactly("A");
    assertThat(savedDeck.cardsInRedBox()).containsExactly("B", "C");
    assertThat(savedDeck.cardsInOrangeBox()).containsExactly("D");
    assertThat(savedDeck.cardsInGreenBox()).isEmpty();
  }

  private void verifyDeckSavedWithBoxRotatedTwice() {
    ArgumentCaptor<Deck> captor = ArgumentCaptor.forClass(Deck.class);
    verify(deckRepository, times(1)).save(captor.capture());
    Deck savedDeck = captor.getValue();
    assertThat(savedDeck.unplayedCards()).containsExactly("A");
    assertThat(savedDeck.cardsInRedBox()).containsExactly("B", "C", "D");
    assertThat(savedDeck.cardsInOrangeBox()).isEmpty();
    assertThat(savedDeck.cardsInGreenBox()).isEmpty();
  }
}
