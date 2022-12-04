package com.dann41.anki.cmd.intrastructure.presentation.cmd.main;

import com.dann41.anki.cmd.intrastructure.presentation.cmd.core.Core;
import com.dann41.anki.core.cardcollection.application.allcollectionsfinder.CardCollectionSummary;

import java.util.List;

public interface MainContract {
  interface View extends Core.View {

    void showCollections();

    void displayCollections(List<CardCollectionSummary> collection);

    void displayError(String errorMessage);

  }

  interface Presenter extends Core.Presenter<View> {

    void createDeck(String collectionId);

    void loadCollections();

  }
}
