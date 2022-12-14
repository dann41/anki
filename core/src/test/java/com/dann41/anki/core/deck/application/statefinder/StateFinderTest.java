package com.dann41.anki.core.deck.application.statefinder;

import com.dann41.anki.core.deck.domain.DeckId;
import com.dann41.anki.core.deck.domain.DeckMother;
import com.dann41.anki.core.deck.domain.DeckRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static com.dann41.anki.core.deck.domain.DeckMother.DECK_ID;
import static com.dann41.anki.core.deck.domain.DeckMother.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class StateFinderTest {

  @Mock
  private DeckRepository deckRepository;

  @InjectMocks
  private StateFinder stateFinder;

  @Test
  public void shouldReturnState() {
    givenDeck();

    StateResponse response = stateFinder.execute(new StateFinderQuery(DECK_ID, USER_ID));

    assertThat(response.totalCards()).isEqualTo(10);
    assertThat(response)
        .isEqualTo(new StateResponse(1, 4, 3, 2, LocalDate.of(2022, 11, 10)));
  }

  private void givenDeck() {
    given(deckRepository.findById(new DeckId(DECK_ID)))
        .willReturn(DeckMother.defaultDeck());
  }

}
