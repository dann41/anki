package com.dann41.anki.cmd.intrastructure.presentation.cmd.main;

import com.dann41.anki.cmd.intrastructure.presentation.cmd.CmdMenu;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.CmdMenuItem;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.CmdTools;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.core.Core;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.core.Navigator;
import com.dann41.anki.core.cardcollection.application.allcollectionsfinder.CardCollectionSummary;

import java.util.List;
import java.util.stream.Collectors;

public class ConsoleView extends Core.BaseView implements MainContract.View {

  private final MainContract.Presenter presenter;
  private final CmdTools cmdTools;

  public ConsoleView(Navigator navigator, MainContract.Presenter presenter) {
    super(navigator);
    this.presenter = presenter;
    cmdTools = new CmdTools();
  }

  @Override
  public void show() {
    configurePresenter(presenter);
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
  public void showCollections() {
    presenter.loadCollections();
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

}
