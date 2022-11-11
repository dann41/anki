package com.dann41.anki.application.deckcreator;

public class CreateDeckCommand {
  private final String deckId;

  public CreateDeckCommand(String deckId) {
    this.deckId = deckId;
  }

  public String deckId() {
    return deckId;
  }
}
