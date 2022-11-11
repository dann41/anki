package com.dann41.anki.core.domain.deck;

public class PickedCard {
  private final String cardId;
  private final String fromBox;

  public PickedCard(String cardId, String fromBox) {
    this.cardId = cardId;
    this.fromBox = fromBox;
  }

  public String cardId() {
    return cardId;
  }

  public String fromBox() {
    return fromBox;
  }
}
