package com.dann41.anki.core.cardcollection.domain.card;

public record Card(String question, String answer) {

  public String id() {
    return question;
  }
}
