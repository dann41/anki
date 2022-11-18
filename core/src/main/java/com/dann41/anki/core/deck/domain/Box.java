package com.dann41.anki.core.deck.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

public record Box(Queue<QuestionId> cards) {
  public Box(List<QuestionId> cards) {
    this(new ConcurrentLinkedQueue<>(cards));
  }

  public static Box create(List<String> idList) {
    return new Box(idList.stream().map(QuestionId::new).collect(Collectors.toList()));
  }

  public Collection<QuestionId> pullAllCards() {
    Collection<QuestionId> cardsCollection = new ArrayList<>(cards);
    cards.clear();
    return cardsCollection;
  }

  public String pickNextCard() {
    return Optional.ofNullable(cards.peek()).map(QuestionId::value).orElse(null);
  }

  public void placeCard(QuestionId questionId) {
    cards.add(questionId);
  }

  public boolean containsCard(QuestionId questionId) {
    return cards.contains(questionId);
  }

  public void removeCard(QuestionId questionId) {
    cards.remove(questionId);
  }

  public void moveCardsTo(Box box) {
    QuestionId card;
    while((card = cards.poll()) != null) {
      box.placeCard(card);
    }
  }
}
