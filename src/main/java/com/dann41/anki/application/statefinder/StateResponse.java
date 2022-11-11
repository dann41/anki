package com.dann41.anki.application.statefinder;

import java.time.LocalDate;
import java.util.Objects;

public class StateResponse {
  private final int unplayedCards;
  private final int greenCards;
  private final int orangeCards;
  private final int redCards;
  private final LocalDate lastSession;

  public StateResponse(int unplayedCards, int greenCards, int orangeCards, int redCards, LocalDate lastSession) {
    this.unplayedCards = unplayedCards;
    this.greenCards = greenCards;
    this.orangeCards = orangeCards;
    this.redCards = redCards;
    this.lastSession = lastSession;
  }

  public int totalCards() {
    return unplayedCards + greenCards + orangeCards + redCards;
  }

  public int unplayedCards() {
    return unplayedCards;
  }

  public int greenCards() {
    return greenCards;
  }

  public int orangeCards() {
    return orangeCards;
  }

  public int redCards() {
    return redCards;
  }

  public LocalDate lastSession() {
    return lastSession;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StateResponse that = (StateResponse) o;
    return unplayedCards == that.unplayedCards && greenCards == that.greenCards && orangeCards == that.orangeCards &&
        redCards == that.redCards && Objects.equals(lastSession, that.lastSession);
  }

  @Override
  public int hashCode() {
    return Objects.hash(unplayedCards, greenCards, orangeCards, redCards, lastSession);
  }
}
