package com.dann41.anki.cmd.infrastructure.presentation.cmd.deckcreation;

import com.dann41.anki.cmd.infrastructure.presentation.cmd.core.Core;
import com.dann41.anki.core.cardcollection.application.allcollectionsfinder.CardCollectionSummary;

import java.util.List;

public interface DeckCreationScreen {
  interface View extends Core.View {

    void displayCollections(List<CardCollectionSummary> collection);

    void displayError(String errorMessage);

    void displayDeckCreated(String deckId);

  }

  interface Presenter extends Core.Presenter<View> {

    void createDeck(String collectionId);

    void loadCollections();

  }
}
