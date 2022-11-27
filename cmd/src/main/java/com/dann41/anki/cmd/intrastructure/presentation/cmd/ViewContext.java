package com.dann41.anki.cmd.intrastructure.presentation.cmd;

public class ViewContext {
  private String currentDeckId;
  private String userId;
  private String username;
  private String collectionId;

  public void login(String userId, String username) {
    this.userId = userId;
    this.username = username;
  }

  public boolean isLoggedIn() {
    return userId != null && !userId.isBlank();
  }

  public void selectCollection(String collectionId) {
    this.collectionId = collectionId;
  }

  public boolean hasCollectionSelected() {
   return collectionId != null && !collectionId.isBlank();
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

  public String selectedCollectionId() {
    return collectionId;
  }

  public String currentDeckId() {
    return currentDeckId;
  }
}