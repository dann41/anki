package com.dann41.anki.core.application.deckcreator;

public class CreateDeckCommand {
  private final String deckId;

  public CreateDeckCommand(String deckId) {
    this.deckId = deckId;
  }

  public String deckId() {
    return deckId;
  }
}
