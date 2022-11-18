package com.dann41.anki.core.deck.infrastructure.repository;

import com.dann41.anki.core.deck.domain.Question;
import com.fasterxml.jackson.annotation.JsonProperty;

public record QuestionDTO(
    @JsonProperty("question") String question,
    @JsonProperty("answer") String answer
) {
  public Question toQuestion() {
    return new Question(question, answer);
  }

  public static QuestionDTO fromQuestion(Question question) {
    return new QuestionDTO(question.question(), question.answer());
  }
}
