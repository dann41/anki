package com.dann41.anki.domain.card;

public class Card {
  private final String question;
  private final String answer;

  public Card(String question, String answer) {
    this.question = question;
    this.answer = answer;
  }

  public String id() {
    return question;
  }

  public String question() {
    return question;
  }

  public String answer() {
    return answer;
  }
}
