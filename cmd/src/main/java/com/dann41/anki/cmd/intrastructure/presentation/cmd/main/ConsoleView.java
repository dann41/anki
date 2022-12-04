package com.dann41.anki.cmd.intrastructure.presentation.cmd.main;

import com.dann41.anki.cmd.intrastructure.presentation.cmd.AnkiState;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.CmdMenu;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.CmdMenuItem;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.CmdTools;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.core.Core;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.core.Navigator;
import com.dann41.anki.core.cardcollection.application.allcollectionsfinder.CardCollectionSummary;
import com.dann41.anki.core.deck.application.cardpicker.CardResponse;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
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
  public void displayState(AnkiState ankiState) {
    System.out.printf("""
            State
            \t- Total Cards: %d
            \t- Cards in green box: %d
            \t- Cards in orange box: %d
            \t- Cards in red box: %d
            \t- Last played: %s]%n""",
        ankiState.totalCards(),
        ankiState.cardsInGreenBox(),
        ankiState.cardsInOrangeBox(),
        ankiState.cardsInRedBox(),
        Optional.ofNullable(ankiState.lastPlayed()).map(ld -> ld.format(DateTimeFormatter.ISO_DATE)).orElse("-"));
  }

  @Override
  public void displayCard(CardResponse cardResponse) {
    System.out.println("NEXT QUESTION: " + cardResponse.question());
    countDown("Answer will reveal in 5 seconds.", 5);

    System.out.println("Answer is: " + cardResponse.answer());
    requestBoxCategorization(cardResponse.id());
  }

  @Override
  public void requestBoxCategorization(String cardId) {
    System.out.println("Where do you want to put the card? (G) Green box, (O) Orange box, (R) Red box: ");
    String input = cmdTools.readLine();
    presenter.solveCard(cardId, input);
  }

  @Override
  public void displayError(String errorMessage) {
    cmdTools.printError(errorMessage);
  }

  @Override
  public void playDeck() {
    presenter.playDeck();
  }

  @Override
  public void displayComeBackLater() {
    cmdTools.printInfo("You're done for now. There are some red cards that you may want to review later.");
  }

  @Override
  public void displayFarewell() {
    cmdTools.printInfo("You're done for today. Come back tomorrow.");
  }

  public void countDown(String message, int seconds) {
    try {
      System.out.println(message);
      for (int i = seconds; i > 1; i--) {
        System.out.print(i + "...");
        Thread.sleep(1000L);
      }
      System.out.println(1);
      Thread.sleep(1000L);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
