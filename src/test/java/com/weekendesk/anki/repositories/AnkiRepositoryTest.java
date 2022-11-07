package com.weekendesk.anki.repositories;

import com.weekendesk.anki.domain.Box;
import com.weekendesk.anki.domain.Card;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class AnkiRepositoryTest {

    private AnkiRepository repository;

    @Before
    public void setup() {
        repository = new AnkiRepository();
    }

    @Test
    public void insertCardIncrementsListBy1() {
        assertTrue(repository.insertCard(new Card()));
        assertThat(repository.getDeck().size(), is(1));
    }

    @Test
    public void updateCardsBoxChangesPropertiesOfCard() {
        repository.insertCard(getCard("Q", "A", Box.ORANGE));
        repository.updateCard(getCard("Q", "A", Box.RED));

        assertThat(repository.getDeck().size(), is(1));
        assertThat(repository.getDeck().get(0).getBox(), is(Box.RED));
    }

    @Test
    public void updateUnknownCardsDoesNothing() {
        repository.insertCard(getCard("Q1", "A", Box.ORANGE));
        repository.updateCard(getCard("Q2", "A", Box.RED));

        assertThat(repository.getDeck().size(), is(1));
        assertThat(repository.getDeck().get(0).getBox(), is(Box.ORANGE));
    }

    @Test
    public void findCardsByBoxReturnsOnlyCardsInThatBox() {
        repository.insertCard(getCard("Q1", "A1", Box.RED));
        repository.insertCard(getCard("Q2", "A2", Box.GREEN));
        repository.insertCard(getCard("Q3", "A3", Box.ORANGE));
        repository.insertCard(getCard("Q4", "A4", Box.GREEN));

        List<Card> result = repository.findCardsInBox(Box.GREEN);

        assertThat(result, is(notNullValue()));
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getBox(), is(Box.GREEN));
        assertThat(result.get(1).getBox(), is(Box.GREEN));
    }

    @Test
    public void rotateCardsUpdatesBoxProperly() {
        repository.insertCard(getCard("Q1", "A1", Box.RED));
        repository.insertCard(getCard("Q2", "A2", Box.GREEN));
        repository.insertCard(getCard("Q3", "A3", Box.ORANGE));
        repository.insertCard(getCard("Q4", "A4", Box.GREEN));

        repository.moveAllCardsToBox(Box.GREEN, Box.ORANGE);

        List<Card> orangeCards = repository.findCardsInBox(Box.ORANGE);
        assertThat(orangeCards, is(notNullValue()));
        assertThat(orangeCards.size(), is(3));
    }

    private Card getCard(String question, String answer, Box box) {
        Card card = new Card();
        card.setQuestion(question);
        card.setAnswer(answer);
        card.setBox(box);
        return card;
    }

}
