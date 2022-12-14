package com.dann41.anki.core.deck.application.statefinder;

import java.time.LocalDate;

public record StateResponse(int unplayedCards, int greenCards, int orangeCards, int redCards, LocalDate lastSession) {

  public int totalCards() {
    return unplayedCards + greenCards + orangeCards + redCards;
  }

}
