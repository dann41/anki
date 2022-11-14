package com.dann41.anki.cmd.intrastructure.presentation.cmd;

import com.dann41.anki.core.application.deck.cardpicker.CardResponse;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class ConsoleView implements View {

  private final Presenter presenter;
  private final CmdTools cmdTools;

  public ConsoleView(Presenter presenter) {
    this.presenter = presenter;
    this.cmdTools = new CmdTools();
  }

  @Override
  public void show() {
    presenter.onViewShown(this);
  }

  public void hide() {
    cmdTools.close();
  }

  @Override
  public void displayWelcome() {
    System.out.println("Welcome to ANKI 2.0");
    System.out.println("The best tool to study stuff");
    System.out.println();
  }

  @Override
  public void displayMainMenu() {
    System.out.println("What do you want to do?");
    System.out.println("1. Play an existing deck");
    System.out.println("2. Create a new deck");
    System.out.print("Choose an option: ");
    String option = cmdTools.readLine();
    if ("1".equals(option)) {
      presenter.loadDecks();
    } else if ("2".equals(option)) {
      presenter.loadCollections();
    } else {
      System.out.println("Invalid option.");
      displayMainMenu();
    }

  }

  @Override
  public void displayDecks(List<String> decks) {
    for (String deck : decks) {
      System.out.println(deck);
    }

    System.out.print("Choose the deck to play: ");
    String deckId = cmdTools.readLine();
    presenter.playDeck(deckId);
  }

  @Override
  public void displayCollections(List<String> collections) {
    for (String colleciton : collections) {
      System.out.println(colleciton);
    }

    System.out.print("Choose a collection to create a deck from: ");
    String collectionId = cmdTools.readLine();
    presenter.createDeck(collectionId);
  }

  @Override
  public void displayState(AnkiState ankiState) {
    System.out.printf("State \n" +
            "\t- Total Cards: %d\n" +
            "\t- Cards in green box: %d \n" +
            "\t- Cards in orange box: %d \n" +
            "\t- Cards in red box: %d \n" +
            "\t- Last played: %s]%n",
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

    //System.out.println("Hit enter to reveal the answer?");
    //readLine();

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
    System.err.println(errorMessage);
  }

  @Override
  public void displayComeBackLater() {
    System.out.println("You're done for now. There are some red cards that you may want to review later.");
  }

  @Override
  public void displayFarewell() {
    System.out.println("You're done for today. Come back tomorrow.");
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
