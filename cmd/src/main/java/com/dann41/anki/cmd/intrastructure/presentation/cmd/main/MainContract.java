package com.dann41.anki.cmd.intrastructure.presentation.cmd.main;

import com.dann41.anki.cmd.intrastructure.presentation.cmd.AnkiState;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.core.Core;
import com.dann41.anki.core.cardcollection.application.allcollectionsfinder.CardCollectionSummary;
import com.dann41.anki.core.deck.application.alldecksfinder.DeckSummary;
import com.dann41.anki.core.deck.application.cardpicker.CardResponse;

import java.util.List;

public interface MainContract {
  interface View extends Core.View {

    void showCollections();

    void displayCollections(List<CardCollectionSummary> collection);

    void displayState(AnkiState ankiState);

    void displayCard(CardResponse cardResponse);

    void requestBoxCategorization(String cardId);

    void displayError(String errorMessage);

    void displayFarewell();

    void displayComeBackLater();

    void playDeck();
  }

  interface Presenter extends Core.Presenter<View> {

    void playDeck();

    void createDeck(String collectionId);

    void solveCard(String cardId, String box);

    void loadCollections();

  }
}
