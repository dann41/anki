package com.dann41.anki.core.domain.deck;

import java.util.Objects;

public class DeckId {

  private final String value;

  public DeckId(String value) {
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
    DeckId deckId = (DeckId) o;
    return Objects.equals(value, deckId.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

}
