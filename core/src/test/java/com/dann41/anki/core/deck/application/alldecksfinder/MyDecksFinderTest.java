package com.dann41.anki.core.deck.application.alldecksfinder;

import com.dann41.anki.core.deck.domain.DeckMother;
import com.dann41.anki.core.deck.domain.DeckRepository;
import com.dann41.anki.core.user.domain.UserId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static com.dann41.anki.core.deck.domain.DeckMother.DECK_ID;
import static com.dann41.anki.core.deck.domain.DeckMother.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MyDecksFinderTest {

    @Mock
    private DeckRepository deckRepository;

    @InjectMocks
    private MyDecksFinder myDecksFinder;

    @Test
    public void shouldReturnDecksResponse() {
        given(deckRepository.findByUserId(new UserId(USER_ID)))
                .willReturn(List.of(DeckMother.defaultDeck()));

        MyDecksFinderResponse response = myDecksFinder.execute(new MyDecksFinderQuery(USER_ID));

        assertThat(response.decks()).hasSize(1);
        assertThat(response.decks())
                .hasSameElementsAs(Collections.singleton(new DeckSummary(DECK_ID, 10, LocalDate.of(2022, 11, 10))));
    }

}