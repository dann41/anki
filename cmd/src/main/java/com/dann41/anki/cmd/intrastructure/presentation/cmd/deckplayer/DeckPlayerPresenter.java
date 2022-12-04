package com.dann41.anki.cmd.intrastructure.presentation.cmd.deckplayer;

import com.dann41.anki.cmd.intrastructure.presentation.cmd.AnkiState;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.ViewContext;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.core.Navigator;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.model.session.SessionInteractor;
import com.dann41.anki.core.deck.application.cardpicker.CardPicker;
import com.dann41.anki.core.deck.application.cardpicker.CardPickerQuery;
import com.dann41.anki.core.deck.application.cardpicker.CardResponse;
import com.dann41.anki.core.deck.application.cardsolver.CardSolver;
import com.dann41.anki.core.deck.application.cardsolver.SolveCardCommand;
import com.dann41.anki.core.deck.application.sessionstarter.SessionStarter;
import com.dann41.anki.core.deck.application.sessionstarter.StartSessionCommand;
import com.dann41.anki.core.deck.application.statefinder.StateFinder;
import com.dann41.anki.core.deck.application.statefinder.StateFinderQuery;
import com.dann41.anki.core.deck.application.statefinder.StateResponse;
import com.dann41.anki.core.deck.domain.DeckNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
public class DeckPlayerPresenter implements DeckPlayerContract.Presenter {
  private static final Map<String, String> BOX_MAPPER = new HashMap<>();

  static {
    BOX_MAPPER.put("g", "green");
    BOX_MAPPER.put("o", "orange");
    BOX_MAPPER.put("r", "red");
  }

  private final SessionStarter sessionStarter;
  private final CardPicker cardPicker;
  private final CardSolver cardSolver;
  private final StateFinder stateFinder;
  private final SessionInteractor sessionInteractor;

  private DeckPlayerContract.View view;
  private Navigator navigator;
  private ViewContext viewContext = new ViewContext();

  public DeckPlayerPresenter(
      SessionStarter sessionStarter,
      CardPicker cardPicker,
      CardSolver cardSolver,
      StateFinder stateFinder,
      SessionInteractor sessionInteractor
  ) {
    this.sessionStarter = sessionStarter;
    this.cardPicker = cardPicker;
    this.cardSolver = cardSolver;
    this.stateFinder = stateFinder;
    this.sessionInteractor = sessionInteractor;
  }

  @Override
  public void onAttachView(DeckPlayerContract.View view) {
    this.view = view;
  }

  @Override
  public void setNavigator(Navigator navigator) {
    this.navigator = navigator;
  }

  @Override
  public void playDeck() {
    loadViewContext();

    startSession();
    displayState();
    displayNextCard();
  }

  @Override
  public void solveCard(String cardId, String box) {
    loadViewContext();

    String boxName = BOX_MAPPER.get(box.toLowerCase(Locale.getDefault()));
    if (boxName == null) {
      view.displayWrongBoxName(cardId, box);
      return;
    }

    cardSolver.execute(new SolveCardCommand(viewContext.currentDeckId(), viewContext.userId(), cardId, boxName));

    displayNextCard();
  }

  private void startSession() {
    var deckId = viewContext.currentDeckId();
    try {
      sessionStarter.execute(new StartSessionCommand(deckId, viewContext.userId()));
      viewContext.playDeck(deckId);
    } catch (DeckNotFoundException e) {
      view.displayDeckNotFound(deckId);
      navigator.back();
    }
  }

  private AnkiState retrieveState() {
    StateResponse stateResponse = stateFinder.execute(new StateFinderQuery(
        viewContext.currentDeckId(),
        viewContext.userId()
    ));
    return toAnkiState(stateResponse);
  }

  private void displayState() {
    view.displayState(retrieveState());
  }

  private void displayNextCard() {
    CardResponse nextCard = cardPicker.execute(
        new CardPickerQuery(viewContext.currentDeckId(), viewContext.userId())
    );

    if (nextCard == null) {
      displayEndSession();
      return;
    }

    view.displayCard(nextCard);
  }

  private void displayEndSession() {
    view.displaySessionEnded();
    navigator.back();
  }

  private AnkiState toAnkiState(StateResponse stateResponse) {
    return new AnkiState(
        viewContext.currentDeckId(),
        stateResponse.totalCards(),
        stateResponse.redCards(),
        stateResponse.orangeCards(),
        stateResponse.greenCards(),
        stateResponse.lastSession()
    );
  }

  private void loadViewContext() {
    var session = sessionInteractor.currentSession();
    if (session != null) {
      viewContext.login(session.userId(), session.username());
    }

    var deckId = sessionInteractor.deckSelected();
    viewContext.playDeck(deckId);
  }
}
