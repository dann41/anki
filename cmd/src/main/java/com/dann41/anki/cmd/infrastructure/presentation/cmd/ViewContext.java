package com.dann41.anki.cmd.infrastructure.presentation.cmd;

public class ViewContext {
  private String currentDeckId;
  private String userId;
  private String username;

  public void login(String userId, String username) {
    this.userId = userId;
    this.username = username;
  }

  public boolean isLoggedIn() {
    return userId != null && !userId.isBlank();
  }

  public void playDeck(String deckId) {
    this.currentDeckId = deckId;
  }

  public String userId() {
    return userId;
  }

  public String username() {
    return username;
  }

  public String currentDeckId() {
    return currentDeckId;
  }
}