package com.dann41.anki.core.deck.application.deckremover;

import com.dann41.anki.core.deck.domain.Deck;
import com.dann41.anki.core.deck.domain.DeckId;
import com.dann41.anki.core.deck.domain.DeckMother;
import com.dann41.anki.core.deck.domain.DeckRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.dann41.anki.core.deck.domain.DeckMother.DECK_ID;
import static com.dann41.anki.core.deck.domain.DeckMother.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeckDeleterTest {

    @Mock
    private DeckRepository deckRepository;

    @InjectMocks
    private DeckDeleter deckDeleter;

    @Test
    public void shouldDeleteDeck() {
        givenDeck(DeckMother.defaultDeck());

        deckDeleter.execute(new DeleteDeckCommand(DECK_ID, USER_ID));

        var captor = ArgumentCaptor.forClass(Deck.class);
        verify(deckRepository, times(1)).save(captor.capture());
        assertThat(captor.getValue().isDeleted()).isTrue();
    }

    private void givenDeck(Deck deck) {
        given(deckRepository.findById(new DeckId(DECK_ID))).willReturn(deck);
    }

}