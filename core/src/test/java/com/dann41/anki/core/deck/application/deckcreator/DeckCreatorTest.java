package com.dann41.anki.core.deck.application.deckcreator;

import com.dann41.anki.core.cardcollection.domain.CardCollection;
import com.dann41.anki.core.cardcollection.domain.CardCollectionId;
import com.dann41.anki.core.cardcollection.domain.CardCollectionRepository;
import com.dann41.anki.core.cardcollection.domain.card.CardDTO;
import com.dann41.anki.core.deck.deckcreator.CreateDeckCommand;
import com.dann41.anki.core.deck.domain.Deck;
import com.dann41.anki.core.deck.domain.DeckAlreadyExistsException;
import com.dann41.anki.core.deck.domain.DeckId;
import com.dann41.anki.core.deck.domain.DeckRepository;
import com.dann41.anki.core.user.domain.UserId;
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
  private static final UserId USER_ID = new UserId("blabla");
  private static final String COLLECTION_ID = "arts";
  @Mock
  private DeckRepository deckRepository;
  @Mock
  private CardCollectionRepository cardCollectionRepository;
  @InjectMocks
  private DeckCreator deckCreator;

  @Test
  public void shouldCreateDeskWithCardCatalog() {
    CreateDeckCommand command = createDeckCommand();
    givenCardCatalog();

    deckCreator.execute(command);

    verifyDeckSavedWithCatalog();
  }

  @Test
  public void shouldThrowExceptionWhenCatalogDoesNotExist() {
    CreateDeckCommand command = createDeckCommand();

    assertThatThrownBy(() -> deckCreator.execute(command))
        .isInstanceOf(CollectionNotFoundException.class);
  }

  @Test
  public void shouldThrowExceptionWhenDeckAlreadyExists() {
    givenExistingDeck();
    CreateDeckCommand command = createDeckCommand();

    assertThatThrownBy(() -> deckCreator.execute(command))
        .isInstanceOf(DeckAlreadyExistsException.class);
  }

  private void givenCardCatalog() {
    given(cardCollectionRepository.findById(new CardCollectionId(COLLECTION_ID)))
        .willReturn(
            new CardCollection(
                COLLECTION_ID,
                COLLECTION_ID,
                COLLECTION_ID,
                Collections.singletonList(
                    new CardDTO("question", "answer")
                )
            )
        );
  }

  private void givenExistingDeck() {
    given(deckRepository.findById(DECK_ID))
        .willReturn(Deck.create(DECK_ID.value(), USER_ID.value(), COLLECTION_ID, Collections.emptyList()));
  }

  private void verifyDeckSavedWithCatalog() {
    ArgumentCaptor<Deck> captor = ArgumentCaptor.forClass(Deck.class);
    verify(deckRepository, times(1)).save(captor.capture());

    Deck savedDeck = captor.getValue();
    assertThat(savedDeck.id()).isEqualTo(DECK_ID);
    assertThat(savedDeck.unansweredCards()).containsExactly("question");
  }

  private static CreateDeckCommand createDeckCommand() {
    return new CreateDeckCommand(DECK_ID.value(), USER_ID.value(), COLLECTION_ID);
  }
}
