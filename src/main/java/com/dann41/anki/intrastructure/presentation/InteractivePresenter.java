package com.dann41.anki.intrastructure.presentation;

import com.dann41.anki.application.cardpicker.CardPicker;
import com.dann41.anki.application.cardpicker.CardResponse;
import com.dann41.anki.application.cardsolver.CardSolver;
import com.dann41.anki.application.cardsolver.SolveCardCommand;
import com.dann41.anki.application.deckcreator.CreateDeckCommand;
import com.dann41.anki.application.deckcreator.DeckCreator;
import com.dann41.anki.application.sessionstarter.SessionStarter;
import com.dann41.anki.application.sessionstarter.StartSessionCommand;
import com.dann41.anki.application.statefinder.StateFinder;
import com.dann41.anki.application.statefinder.StateResponse;
import com.dann41.anki.domain.deck.DeckNotFoundException;
import com.dann41.anki.intrastructure.AppContext;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class InteractivePresenter implements Presenter {
  private final AppContext appContext;
  private View view;
  private final String deckId;
  private static final Map<String, String> BOX_MAPPER = new HashMap<>();

  static {
    BOX_MAPPER.put("g", "green");
    BOX_MAPPER.put("o", "orange");
    BOX_MAPPER.put("r", "red");
  }

  public InteractivePresenter(String deckId, AppContext appContext) {
    // TODO inject use cases instead of AppContext
    this.deckId = deckId;
    this.appContext = appContext;
  }

  @Override
  public void onViewShown(View view) {
    this.view = view;
    onStart();
  }

  public void onStart() {
    view.displayWelcome();
    startSession();
    displayState();
    displayNextCard();
  }

  private void startSession() {
    SessionStarter sessionStarter = appContext.get(SessionStarter.class);
    try {
      sessionStarter.execute(new StartSessionCommand(deckId));
    } catch (DeckNotFoundException e) {
      DeckCreator deckCreator = appContext.get(DeckCreator.class);
      deckCreator.execute(new CreateDeckCommand(deckId));
      sessionStarter.execute(new StartSessionCommand(deckId));
    }
  }

  private AnkiState retrieveState() {
    StateFinder stateFinder = appContext.get(StateFinder.class);
    StateResponse stateResponse = stateFinder.execute(deckId);
    return toAnkiState(stateResponse);
  }

  private void displayState() {
    view.displayState(retrieveState());
  }

  private void displayNextCard() {
    CardResponse nextCard = getNextCard();

    if (nextCard == null) {
      displayEndSession();
      return;
    }

    view.displayCard(nextCard);
  }

  private void displayEndSession() {
    AnkiState ankiState = retrieveState();
    if (ankiState.cardsInRedBox() != 0) {
      view.displayComeBackLater();
    } else {
      view.displayFarewell();
    }
  }

  private CardResponse getNextCard() {
    CardPicker cardPicker = appContext.get(CardPicker.class);
    return cardPicker.execute(deckId);
  }

  private static AnkiState toAnkiState(StateResponse stateResponse) {
    return new AnkiState(
        stateResponse.totalCards(),
        stateResponse.redCards(),
        stateResponse.orangeCards(),
        stateResponse.greenCards(),
        stateResponse.lastSession()
    );
  }

  @Override
  public void solveCard(String cardId, String box) {
    String boxName = BOX_MAPPER.get(box.toLowerCase(Locale.getDefault()));
    if (boxName == null) {
      view.displayError("Unknown box name " + box);
      view.requestBoxCategorization(cardId);
      return;
    }

    CardSolver cardSolver = appContext.get(CardSolver.class);
    cardSolver.execute(new SolveCardCommand(deckId, cardId, boxName));

    displayNextCard();
  }
}
