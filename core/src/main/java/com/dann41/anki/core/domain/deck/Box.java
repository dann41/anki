package com.dann41.anki.core.domain.deck;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

public record Box(Queue<CardId> cards) {
  public Box(List<CardId> cards) {
    this(new ConcurrentLinkedQueue<>(cards));
  }

  public static Box create(List<String> idList) {
    return new Box(idList.stream().map(CardId::new).collect(Collectors.toList()));
  }

  public Collection<CardId> pullAllCards() {
    Collection<CardId> cardsCollection = new ArrayList<>(cards);
    cards.clear();
    return cardsCollection;
  }

  public String pickNextCard() {
    return Optional.ofNullable(cards.peek()).map(CardId::value).orElse(null);
  }

  public void placeCard(CardId cardId) {
    cards.add(cardId);
  }

  public boolean containsCard(CardId cardId) {
    return cards.contains(cardId);
  }

  public void removeCard(CardId cardId) {
    cards.remove(cardId);
  }

  public void moveCardsTo(Box box) {
    CardId card;
    while((card = cards.poll()) != null) {
      box.placeCard(card);
    }
  }
}
