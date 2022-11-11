package com.dann41.anki.cmd.intrastructure.presentation;

import com.dann41.anki.core.application.cardpicker.CardResponse;

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
