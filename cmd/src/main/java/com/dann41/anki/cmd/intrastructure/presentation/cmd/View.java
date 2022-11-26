package com.dann41.anki.cmd.intrastructure.presentation.cmd;

import com.dann41.anki.core.cardcollection.application.allcollectionsfinder.CardCollectionSummary;
import com.dann41.anki.core.deck.application.alldecksfinder.DeckSummary;
import com.dann41.anki.core.deck.application.cardpicker.CardResponse;

import java.util.List;

public interface View {

  void show();

  void displayWelcome();

  void displayLogin();

  void displayMessage(String message);

  void displayMainMenu();

  void displayDecks(List<DeckSummary> decks);

  void displayCollections(List<CardCollectionSummary> collection);

  void displayState(AnkiState ankiState);

  void displayCard(CardResponse cardResponse);

  void requestBoxCategorization(String cardId);

  void displayError(String errorMessage);

  void displayFarewell();

  void displayComeBackLater();

  void displayLoginSucceed();
}
