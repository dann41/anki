package com.weekendesk.anki.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weekendesk.anki.domain.Box;
import com.weekendesk.anki.domain.Card;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class AnkiRepository implements CardRepository, SessionRepository, AppRepository {

    private static final String FILENAME = "anki.json";

    private List<Card> deck = new LinkedList<>();
    private long lastSessionStarted;

    @Override
    public boolean insertCard(Card card) {
        return deck.add(card);
    }

    @Override
    public void updateCard(Card card) {
        int index = deck.indexOf(card);
        if (index != -1) {
            Card originalCard = deck.get(index);
            originalCard.setQuestion(card.getQuestion());
            originalCard.setAnswer(card.getAnswer());
            originalCard.setBox(card.getBox());
        } else {
            System.err.println("Card not found");
        }
    }

    public List<Card> getDeck() {
        return deck;
    }

    @Override
    public List<Card> findCardsInBox(Box box) {
        return deck.stream()
                .filter(c -> c.getBox() == box)
                .collect(Collectors.toList());
    }

    @Override
    public void moveAllCardsToBox(Box from, Box to) {
        deck.stream()
                .filter(c -> c.getBox() == from)
                .forEach(c -> c.setBox(to));
    }

    @Override
    public long getLastSessionStarted() {
        return lastSessionStarted;
    }

    @Override
    public void setLastSessionStarted(long lastSessionStarted) {
        this.lastSessionStarted = lastSessionStarted;
    }

    @Override
    public void load() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            AnkiState state = mapper.readValue(new File(FILENAME), AnkiState.class);
            deck = state.deck;
            lastSessionStarted = state.lastSessionStarted;
        } catch (IOException e) {
            deck = new LinkedList<>();
        }
    }

    @Override
    public void save() {
        AnkiState ankiState = new AnkiState(deck, lastSessionStarted);

        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(FILENAME), ankiState);
        } catch (IOException e) {
            System.err.println("Could't save state");
            e.printStackTrace();
        }
    }

    private static class AnkiState {

        public List<Card> deck;
        public long lastSessionStarted;

        // Required for Jackson
        public AnkiState() {

        }

        public AnkiState(List<Card> deck, long lastSessionStarted) {
            this.deck = deck;
            this.lastSessionStarted = lastSessionStarted;
        }
    }

}
