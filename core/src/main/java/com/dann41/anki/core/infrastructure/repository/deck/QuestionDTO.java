package com.dann41.anki.core.infrastructure.repository.deck;

import com.dann41.anki.core.domain.deck.Question;
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
