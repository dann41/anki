package com.dann41.anki.core.deck.domain;

import java.util.List;

public record Questions(List<Question> value) {
  @Override
  public List<Question> value() {
    return value;
  }

  public Question findById(QuestionId questionId) {
    return value.stream()
        .filter(question -> questionId.value().equals(question.id()))
        .findFirst()
        .orElse(null);
  }
}
