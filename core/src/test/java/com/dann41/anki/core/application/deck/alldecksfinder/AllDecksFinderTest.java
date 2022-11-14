package com.dann41.anki.core.application.deck.alldecksfinder;

import com.dann41.anki.core.domain.DeckMother;
import com.dann41.anki.core.domain.deck.DeckRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.dann41.anki.core.domain.DeckMother.DECK_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AllDecksFinderTest {

  @Mock
  private DeckRepository deckRepository;

  @InjectMocks
  private AllDecksFinder allDecksFinder;

  @Test
  public void shouldReturnDecksResponse() {
    given(deckRepository.findAll())
        .willReturn(List.of(DeckMother.defaultDeck()));

    DecksResponse response = allDecksFinder.execute();

    assertThat(response.decks()).hasSize(1);
    assertThat(response.decks())
        .hasSameElementsAs(Collections.singleton(new DeckSummary(DECK_ID, 10, LocalDate.of(2022, 11, 10))));
  }

}