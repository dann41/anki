package com.dann41.anki.cmd.intrastructure.presentation;

import com.dann41.anki.core.application.cardpicker.CardPicker;
import com.dann41.anki.core.application.cardpicker.CardResponse;
import com.dann41.anki.core.application.cardsolver.CardSolver;
import com.dann41.anki.core.application.cardsolver.SolveCardCommand;
import com.dann41.anki.core.application.deckcreator.CreateDeckCommand;
import com.dann41.anki.core.application.deckcreator.DeckCreator;
import com.dann41.anki.core.application.sessionstarter.SessionStarter;
import com.dann41.anki.core.application.sessionstarter.StartSessionCommand;
import com.dann41.anki.core.application.statefinder.StateFinder;
import com.dann41.anki.core.application.statefinder.StateResponse;
import com.dann41.anki.core.domain.deck.DeckNotFoundException;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class InteractivePresenter implements Presenter {
  private final ApplicationContext appContext;
  private View view;
  private final String deckId;
  private static final Map<String, String> BOX_MAPPER = new HashMap<>();

  static {
    BOX_MAPPER.put("g", "green");
    BOX_MAPPER.put("o", "orange");
    BOX_MAPPER.put("r", "red");
  }

  public InteractivePresenter(String deckId, ApplicationContext appContext) {
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
    SessionStarter sessionStarter = appContext.getBean(SessionStarter.class);
    try {
      sessionStarter.execute(new StartSessionCommand(deckId));
    } catch (DeckNotFoundException e) {
      DeckCreator deckCreator = appContext.getBean(DeckCreator.class);
      deckCreator.execute(new CreateDeckCommand(deckId));
      sessionStarter.execute(new StartSessionCommand(deckId));
    }
  }

  private AnkiState retrieveState() {
    StateFinder stateFinder = appContext.getBean(StateFinder.class);
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
    CardPicker cardPicker = appContext.getBean(CardPicker.class);
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

    CardSolver cardSolver = appContext.getBean(CardSolver.class);
    cardSolver.execute(new SolveCardCommand(deckId, cardId, boxName));

    displayNextCard();
  }
}
