package com.dann41.anki.core.application.sessionstarter;

public class StartSessionCommand {
  private final String deckId;

  public StartSessionCommand(String deckId) {
    this.deckId = deckId;
  }

  public String deckId() {
    return deckId;
  }
}
