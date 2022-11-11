package com.dann41.anki.intrastructure.presentation;

import java.time.LocalDate;

public class AnkiState {
  private final int totalCards;
  private final int cardsInRedBox;
  private final int cardsInOrangeBox;
  private final int cardsInGreenBox;
  private final LocalDate lastPlayed;

  public AnkiState(int totalCards, int cardsInRedBox, int cardsInOrangeBox, int cardsInGreenBox, LocalDate lastPlayed) {
    this.totalCards = totalCards;
    this.cardsInRedBox = cardsInRedBox;
    this.cardsInOrangeBox = cardsInOrangeBox;
    this.cardsInGreenBox = cardsInGreenBox;
    this.lastPlayed = lastPlayed;
  }

  public int totalCards() {
    return totalCards;
  }

  public int cardsInGreenBox() {
    return cardsInGreenBox;
  }

  public int cardsInOrangeBox() {
    return cardsInOrangeBox;
  }

  public int cardsInRedBox() {
    return cardsInRedBox;
  }

  public LocalDate lastPlayed() {
    return lastPlayed;
  }
}
