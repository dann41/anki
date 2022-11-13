package com.dann41.anki.core.application.deckcreator;

import com.dann41.anki.core.application.deck.deckcreator.CreateDeckCommand;
import com.dann41.anki.core.application.deck.deckcreator.DeckCreator;
import com.dann41.anki.core.domain.card.Card;
import com.dann41.anki.core.domain.card.CardRepository;
import com.dann41.anki.core.domain.deck.Deck;
import com.dann41.anki.core.domain.deck.DeckAlreadyExistsException;
import com.dann41.anki.core.domain.deck.DeckId;
import com.dann41.anki.core.domain.deck.DeckRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DeckCreatorTest {
  private static final DeckId DECK_ID = new DeckId("1234");
  @Mock
  private DeckRepository deckRepository;
  @Mock
  private CardRepository cardRepository;
  @InjectMocks
  private DeckCreator deckCreator;

  @Test
  public void shouldCreateDeck() {
    CreateDeckCommand command = new CreateDeckCommand(DECK_ID.value());

    deckCreator.execute(command);

    verifyDeckSaved();
  }

  @Test
  public void shouldCreateDeskWithCardCatalog() {
    CreateDeckCommand command = new CreateDeckCommand(DECK_ID.value());
    givenCardCatalog();

    deckCreator.execute(command);

    verifyDeckSavedWithCatalog();
  }

  @Test
  public void shouldThrowExceptionWhenDeckAlreadyExists() {
    givenExistingDeck();
    CreateDeckCommand command = new CreateDeckCommand(DECK_ID.value());

    assertThatThrownBy(() -> deckCreator.execute(command))
        .isInstanceOf(DeckAlreadyExistsException.class);
  }

  private void givenCardCatalog() {
    given(cardRepository.getAll()).willReturn(
        Collections.singletonList(
            new Card("question", "answer")
        )
    );
  }

  private void givenExistingDeck() {
    given(deckRepository.findById(DECK_ID))
        .willReturn(Deck.create(DECK_ID.value(), Collections.emptyList()));
  }

  private void verifyDeckSaved() {
    ArgumentCaptor<Deck> captor = ArgumentCaptor.forClass(Deck.class);
    verify(deckRepository, times(1)).save(captor.capture());

    Deck savedDeck = captor.getValue();
    assertThat(savedDeck.id()).isEqualTo(DECK_ID);
  }

  private void verifyDeckSavedWithCatalog() {
    ArgumentCaptor<Deck> captor = ArgumentCaptor.forClass(Deck.class);
    verify(deckRepository, times(1)).save(captor.capture());

    Deck savedDeck = captor.getValue();
    assertThat(savedDeck.id()).isEqualTo(DECK_ID);
    assertThat(savedDeck.unplayedCards()).containsExactly("question");
  }

}
