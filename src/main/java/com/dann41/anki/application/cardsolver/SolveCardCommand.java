package com.dann41.anki.application.cardsolver;

public class SolveCardCommand {
  private final String deckId;
  private final String cardId;
  private final String boxName;

  public SolveCardCommand(String deckId, String cardId, String boxName) {
    this.deckId = deckId;
    this.cardId = cardId;
    this.boxName = boxName;
  }

  public String deckId() {
    return deckId;
  }

  public String cardId() {
    return cardId;
  }

  public String boxName() {
    return boxName;
  }
}
