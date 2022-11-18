package com.dann41.anki.core.deck.domain;

public record Question(String question, String answer) {
  public String id() {
    return question;
  }
}
