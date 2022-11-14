package com.dann41.anki.cmd.intrastructure.presentation.cmd;

import com.dann41.anki.core.application.deck.cardpicker.CardResponse;

import java.util.List;

public interface View {

  void show();

  void displayWelcome();

  void displayMainMenu();

  void displayDecks(List<String> decks);

  void displayCollections(List<String> collection);

  void displayState(AnkiState ankiState);

  void displayCard(CardResponse cardResponse);

  void requestBoxCategorization(String cardId);

  void displayError(String errorMessage);

  void displayFarewell();

  void displayComeBackLater();
}
