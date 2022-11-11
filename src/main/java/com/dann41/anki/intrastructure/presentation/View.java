package com.dann41.anki.intrastructure.presentation;

import com.dann41.anki.application.cardpicker.CardResponse;

public interface View {

  void show();

  void displayWelcome();

  void displayState(AnkiState ankiState);

  void displayCard(CardResponse cardResponse);

  void requestBoxCategorization(String cardId);

  void displayError(String errorMessage);

  void displayFarewell();

  void displayComeBackLater();
}
