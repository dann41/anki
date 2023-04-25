package com.dann41.anki.cmd.infrastructure.presentation.cmd.deckcreation;

import com.dann41.anki.cmd.infrastructure.presentation.cmd.CmdMenu;
import com.dann41.anki.cmd.infrastructure.presentation.cmd.CmdMenuItem;
import com.dann41.anki.cmd.infrastructure.presentation.cmd.CmdTools;
import com.dann41.anki.cmd.infrastructure.presentation.cmd.core.BaseView;
import com.dann41.anki.cmd.infrastructure.presentation.cmd.core.Navigator;
import com.dann41.anki.core.cardcollection.application.allcollectionsfinder.CardCollectionSummary;

import java.util.List;
import java.util.stream.Collectors;

public class DeckCreationView extends BaseView implements DeckCreationScreen.View {

  private final DeckCreationScreen.Presenter presenter;
  private final CmdTools cmdTools;

  public DeckCreationView(Navigator navigator, DeckCreationScreen.Presenter presenter, CmdTools cmdTools) {
    super(navigator);
    this.presenter = presenter;
    this.cmdTools = cmdTools;
  }

  @Override
  public void show() {
    configurePresenter(presenter);
    presenter.loadCollections();
  }

  private String getCollectionString(CardCollectionSummary collection) {
    return """
        %s (ID: %s)
        %s
        Number of questions: %d
        """.formatted(
        collection.name(),
        collection.id(),
        collection.description(),
        collection.numberOfQuestions()
    );
  }

  @Override
  public void displayCollections(List<CardCollectionSummary> collections) {
    var collectionSelector = new CmdMenu(
        "--- Collections ---",
        collections.stream().map(
            collection -> CmdMenuItem.of(
                getCollectionString(collection),
                collection.id()
            )
        ).collect(Collectors.toList()),
        "Choose a collection to create a deck from:"
    );

    String collectionId = cmdTools.printMenu(collectionSelector);
    if (collectionId.isEmpty()) {
      goBack();
      return;
    }

    presenter.createDeck(collectionId);
  }


  @Override
  public void displayError(String errorMessage) {
    cmdTools.printError(errorMessage);
  }

  @Override
  public void displayDeckCreated(String deckId) {
    cmdTools.printInfo("Deck created with id " + deckId);
  }

}
