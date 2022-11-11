package com.dann41.anki.core.domain.deck;

import java.util.Objects;

public class CardId {

  private final String value;

  public CardId(String value) {
    this.value = value;
  }

  public String value() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CardId cardId = (CardId) o;
    return Objects.equals(value, cardId.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
