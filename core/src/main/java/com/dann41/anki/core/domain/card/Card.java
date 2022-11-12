package com.dann41.anki.core.domain.card;

public record Card(String question, String answer) {

  public String id() {
    return question;
  }
}
