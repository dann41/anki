package com.dann41.anki.cmd.intrastructure.presentation.cmd;

public interface Presenter {

  void onViewShown(View view);
  void register(String username, String password);

  void login(String user, String password);

  void playDeck(String deckId);

  void createDeck(String collectionId);

  void solveCard(String cardId, String box);

  void loadDecks();

  void loadCollections();

  void createCollection(String resourceName, String collectionName);

}
