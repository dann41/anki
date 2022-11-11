package com.dann41.anki.intrastructure.presentation.cmd;

import com.dann41.anki.application.cardpicker.CardResponse;
import com.dann41.anki.intrastructure.presentation.AnkiState;
import com.dann41.anki.intrastructure.presentation.Presenter;
import com.dann41.anki.intrastructure.presentation.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class ConsoleView implements View {

  private final Presenter presenter;
  private final BufferedReader reader;

  public ConsoleView(Presenter presenter) {
    this.presenter = presenter;
    this.reader = new BufferedReader(new InputStreamReader(System.in));
  }

  @Override
  public void show() {
    presenter.onViewShown(this);
  }

  @Override
  public void displayWelcome() {
    System.out.println("Welcome to ANKI 2.0");
    System.out.println("The best tool to study stuff");
    System.out.println();
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

  private String readLine() {
    try {
      return reader.readLine();
    } catch (IOException e) {
      return null;
    }
  }

  @Override
  public void requestBoxCategorization(String cardId) {
    System.out.println("Where do you want to put the card? (G) Green box, (O) Orange box, (R) Red box: ");
    String input = readLine();
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
    System.out.print(message);
    for(int i = seconds; i > 0; i--) {
      try {
        Thread.sleep(i);
        System.out.print(" ..." + i);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }

  }
}
