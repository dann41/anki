package com.weekendesk.anki.services;

import com.weekendesk.anki.domain.Box;
import com.weekendesk.anki.domain.Card;
import com.weekendesk.anki.repositories.CardRepository;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

public class CardServiceImpl implements CardService {

    private final CardRepository repository;

    public CardServiceImpl(CardRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean addCard(Card card) {
        return repository.insertCard(card);
    }

    @Override
    public long countCards(Box box) {
        return repository.findCardsInBox(box).size();
    }

    @Override
    public Queue<Card> getCardsSorted(Box box) {
        return repository.findCardsInBox(box)
                .stream()
                .sorted(Comparator.comparing(Card::getQuestion))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public void moveCardToBox(Card card, Box box) {
        card.setBox(box);
        repository.updateCard(card);
    }

    @Override
    public void rotateCards() {
        repository.moveAllCardsToBox(Box.ORANGE, Box.RED);
        repository.moveAllCardsToBox(Box.GREEN, Box.ORANGE);
    }
}
