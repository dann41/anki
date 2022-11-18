package com.dann41.anki.core.cardcollection.domain;

import com.dann41.anki.core.cardcollection.domain.card.CardDTO;

import java.util.List;

public class CardCollection {

  private final CardCollectionId cardCollectionId;
  private final Name name;
  private final Description description;
  private final Cards cards;

  public CardCollection(String cardCollectionId, String name, String description, List<CardDTO> cards) {
    this.cardCollectionId = new CardCollectionId(cardCollectionId);
    this.name = new Name(name);
    this.description = new Description(description);
    this.cards = new Cards(cards);
  }

  public String id() {
    return cardCollectionId.value();
  }

  public String name() {
    return name.value();
  }

  public String description() {
    return description.value();
  }

  public List<CardDTO> cards() {
    return cards.value();
  }
}
