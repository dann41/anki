package com.dann41.anki.cmd.intrastructure.presentation.cmd.deckplayer;

import com.dann41.anki.cmd.intrastructure.presentation.cmd.AnkiState;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.CmdTools;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.core.Core;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.core.Navigator;
import com.dann41.anki.core.deck.application.cardpicker.CardResponse;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class DeckPlayerView extends Core.BaseView implements DeckPlayerContract.View {
  private final DeckPlayerContract.Presenter presenter;
  private final CmdTools cmdTools;

  public DeckPlayerView(Navigator navigator, DeckPlayerContract.Presenter presenter, CmdTools cmdTools) {
    super(navigator);
    this.presenter = presenter;
    this.cmdTools = cmdTools;

  }

  @Override
  public void show() {
    configurePresenter(presenter);
    presenter.playDeck();
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
  public void displaySessionEnded() {
    cmdTools.printInfo("Deck training completed. Try to play again tomorrow.");
  }

  @Override
  public void displayWrongBoxName(String cardId, String box) {
    cmdTools.printError("Unknown box name " + box);

    requestBoxCategorization(cardId);
  }

  @Override
  public void displayDeckNotFound(String deckId) {
    cmdTools.printError("Cannot find deck for id " + deckId);
  }

  private void countDown(String message, int seconds) {
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
