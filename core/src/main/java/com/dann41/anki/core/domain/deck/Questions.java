package com.dann41.anki.core.domain.deck;

import java.util.List;

public record Questions(List<Question> value) {
  @Override
  public List<Question> value() {
    return value;
  }
}
