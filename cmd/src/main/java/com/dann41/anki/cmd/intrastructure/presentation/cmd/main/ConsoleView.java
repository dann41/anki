package com.dann41.anki.cmd.intrastructure.presentation.cmd.main;

import com.dann41.anki.cmd.intrastructure.presentation.cmd.AnkiState;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.CmdMenu;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.CmdMenuItem;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.CmdTools;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.core.Core;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.core.Navigator;
import com.dann41.anki.core.cardcollection.application.allcollectionsfinder.CardCollectionSummary;
import com.dann41.anki.core.deck.application.alldecksfinder.DeckSummary;
import com.dann41.anki.core.deck.application.cardpicker.CardResponse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ConsoleView extends Core.BaseView implements MainContract.View {

  public static final String LIST_DECKS = "1";
  public static final String LIST_COLLECTIONS = "2";
  public static final String IMPORT_COLLECTIONS = "3";
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

  @Override
  public void displaySignUp() {
    System.out.println("--- REGISTER ---");
    System.out.print("Username: ");
    String username = cmdTools.readLine();
    System.out.print("Password: ");
    String password = cmdTools.readLine();

    presenter.register(username, password);
  }

  @Override
  public void displayMainMenu() {
    var menu = new CmdMenu(
        "--- ANKI Menu ---",
        List.of(
            CmdMenuItem.of(" 1. Play an existing deck", "1"),
            CmdMenuItem.of(" 2. Create a new deck", "2"),
            CmdMenuItem.of(" 3. Import collection from file", "3")
        ),
        "Choose an option: "
    );

    String option = cmdTools.printMenu(menu);

    switch (option) {
      case LIST_DECKS -> presenter.loadDecks();
      case LIST_COLLECTIONS -> presenter.loadCollections();
      case IMPORT_COLLECTIONS -> displayCollectionImportDialog();
      default -> displayMainMenu();
    }
  }

  private void displayCollectionImportDialog() {
    System.out.println("Select file to import (e.g. core/src/main/resources/cards.tsv): ");
    String resourceName = cmdTools.readLine();
    System.out.println("Name of the collection: ");
    String collectionName = cmdTools.readLine();
    presenter.createCollection(resourceName, collectionName);
  }

  @Override
  public void displayLoginSucceed() {
    cmdTools.printInfo("Login succeed");
  }

  @Override
  public void displayMessage(String message) {
    cmdTools.printInfo(message);
  }

  @Override
  public void displayDecks(List<DeckSummary> decks) {
    if (decks.isEmpty()) {
      cmdTools.printInfo("No decks found. Please create one");
      displayMainMenu();
    }

    var deckSelector = new CmdMenu(
        "--- My decks ---",
        decks.stream().map(
            deck -> CmdMenuItem.of(
                deck.id() + " - " + deck.numberOfQuestions() + " questions. Last played on " +
                    Optional.ofNullable(deck.lastSession()).map(LocalDate::toString).orElse("never"),
                deck.id()
            )
        ).collect(Collectors.toList()),
        "Choose the deck to play: "
    );

    String deckId = cmdTools.printMenu(deckSelector);

    if (deckId.isEmpty()) {
      System.out.println("No deck selected. Back to main menu");
      displayMainMenu();
      return;
    }

    presenter.playDeck(deckId);
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
      displayMainMenu();
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
