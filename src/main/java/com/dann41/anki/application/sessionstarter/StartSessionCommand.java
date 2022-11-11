package com.dann41.anki.application.sessionstarter;

public class StartSessionCommand {
  private final String deckId;

  public StartSessionCommand(String deckId) {
    this.deckId = deckId;
  }

  public String deckId() {
    return deckId;
  }
}
