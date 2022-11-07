package com.weekendesk.anki.services;

import com.weekendesk.anki.domain.Box;
import com.weekendesk.anki.domain.Card;
import com.weekendesk.anki.repositories.CardRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Queue;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CardServiceImplTest {

    private CardServiceImpl service;

    @Mock
    private CardRepository cardRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        service = new CardServiceImpl(cardRepository);
    }

    @Test
    public void addCardPropagatesRepoAnswer() {
        when(cardRepository.insertCard(any())).thenReturn(true);

        boolean result = service.addCard(new Card());
        assertThat(result, is(true));
        verify(cardRepository, times(1)).insertCard(any());
    }

    @Test
    public void countCardsReturnNumberOfCards() {
        when(cardRepository.findCardsInBox(any())).thenReturn(
                Arrays.asList(
                        getCard("Q1", "A1", Box.ORANGE),
                        getCard("Q2", "A2", Box.ORANGE),
                        getCard("Q3", "A3", Box.ORANGE)
                )
        );

        long result = service.countCards(Box.ORANGE);

        assertThat(result, is(3L));
    }

    @Test
    public void getCardsSortedReturnsAQueueInOrderByQuestion() {
        when(cardRepository.findCardsInBox(any())).thenReturn(
                Arrays.asList(
                        getCard("Q R", "A1", Box.ORANGE),
                        getCard("Q B", "A2", Box.ORANGE),
                        getCard("Q D", "A3", Box.ORANGE)
                )
        );

        Queue<Card> result = service.getCardsSorted(Box.ORANGE);
        assertThat(result, is(notNullValue()));
        assertThat(result.poll().getQuestion(), is("Q B"));
        assertThat(result.poll().getQuestion(), is("Q D"));
        assertThat(result.poll().getQuestion(), is("Q R"));
    }

    @Test
    public void rotateCardsUpdatesFirstOrangeBoxAndThenGreenBox() {
        service.rotateCards();

        InOrder orderVerifier = Mockito.inOrder(cardRepository);
        orderVerifier.verify(cardRepository, times(1)).moveAllCardsToBox(Box.ORANGE, Box.RED);
        orderVerifier.verify(cardRepository, times(1)).moveAllCardsToBox(Box.GREEN, Box.ORANGE);
    }

    private Card getCard(String question, String answer, Box box) {
        Card card = new Card();
        card.setQuestion(question);
        card.setAnswer(answer);
        card.setBox(box);
        return card;
    }
}
