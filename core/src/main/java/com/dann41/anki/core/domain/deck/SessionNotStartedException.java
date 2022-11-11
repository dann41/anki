package com.dann41.anki.core.domain.deck;

public class SessionNotStartedException extends RuntimeException {
  public SessionNotStartedException(DeckId id) {
    super("Session not started for deck " + id.value());
  }
}
