package com.dann41.anki.cmd.intrastructure.presentation.cmd.main;

import com.dann41.anki.cmd.intrastructure.presentation.cmd.AnkiState;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.ViewContext;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.core.Navigator;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.model.session.SessionInteractor;
import com.dann41.anki.core.cardcollection.application.allcollectionsfinder.AllCollectionsFinder;
import com.dann41.anki.core.deck.application.cardpicker.CardPicker;
import com.dann41.anki.core.deck.application.cardpicker.CardPickerQuery;
import com.dann41.anki.core.deck.application.cardpicker.CardResponse;
import com.dann41.anki.core.deck.application.cardsolver.CardSolver;
import com.dann41.anki.core.deck.application.cardsolver.SolveCardCommand;
import com.dann41.anki.core.deck.application.deckcreator.CollectionNotFoundException;
import com.dann41.anki.core.deck.application.deckcreator.CreateDeckCommand;
import com.dann41.anki.core.deck.application.deckcreator.DeckCreator;
import com.dann41.anki.core.deck.application.sessionstarter.SessionStarter;
import com.dann41.anki.core.deck.application.sessionstarter.StartSessionCommand;
import com.dann41.anki.core.deck.application.statefinder.StateFinder;
import com.dann41.anki.core.deck.application.statefinder.StateFinderQuery;
import com.dann41.anki.core.deck.application.statefinder.StateResponse;
import com.dann41.anki.core.deck.domain.DeckNotFoundException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

@Service
public class InteractivePresenter implements MainContract.Presenter {
  private static final Map<String, String> BOX_MAPPER = new HashMap<>();

  static {
    BOX_MAPPER.put("g", "green");
    BOX_MAPPER.put("o", "orange");
    BOX_MAPPER.put("r", "red");
  }

  private final ApplicationContext appContext;
  private final ViewContext viewContext = new ViewContext();
  private final SessionInteractor sessionInteractor;
  private MainContract.View view;
  private Navigator navigator;

  public InteractivePresenter(ApplicationContext appContext, SessionInteractor sessionInteractor) {
    // TODO inject use cases instead of AppContext
    this.appContext = appContext;
    this.sessionInteractor = sessionInteractor;

    loadViewContext();
  }

  private void loadViewContext() {
    var session = sessionInteractor.currentSession();
    if (session != null) {
      viewContext.login(session.userId(), session.username());
    }

    var deckId = sessionInteractor.deckSelected();
    viewContext.playDeck(deckId);
  }

  @Override
  public void onAttachView(MainContract.View view) {
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
  public void createDeck(String collectionId) {
    loadViewContext();

    String deckId = UUID.randomUUID().toString();

    var deckCreator = appContext.getBean(DeckCreator.class);
    try {
      deckCreator.execute(
          new CreateDeckCommand(
              deckId,
              viewContext.userId(),
              collectionId
          )
      );

      navigator.openUserMenuScreen();
    } catch (CollectionNotFoundException exception) {
      view.displayError("Cannot find collection for id " + collectionId);
      navigator.back();
    }
  }

  private void startSession() {
    var deckId = viewContext.currentDeckId();
    var sessionStarter = appContext.getBean(SessionStarter.class);
    try {
      sessionStarter.execute(new StartSessionCommand(deckId, viewContext.userId()));
      viewContext.playDeck(deckId);
    } catch (DeckNotFoundException e) {
      view.displayError("Cannot find deck for id " + deckId);
      navigator.back();
    }
  }

  private AnkiState retrieveState() {
    StateFinder stateFinder = appContext.getBean(StateFinder.class);
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

    navigator.back();
  }

  private CardResponse getNextCard() {
    CardPicker cardPicker = appContext.getBean(CardPicker.class);
    return cardPicker.execute(new CardPickerQuery(viewContext.currentDeckId(), viewContext.userId()));
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

  @Override
  public void solveCard(String cardId, String box) {
    loadViewContext();

    String boxName = BOX_MAPPER.get(box.toLowerCase(Locale.getDefault()));
    if (boxName == null) {
      view.displayError("Unknown box name " + box);
      view.requestBoxCategorization(cardId);
      return;
    }

    CardSolver cardSolver = appContext.getBean(CardSolver.class);
    cardSolver.execute(new SolveCardCommand(viewContext.currentDeckId(), viewContext.userId(), cardId, boxName));

    displayNextCard();
  }

  @Override
  public void loadCollections() {
    loadViewContext();

    var allCollectionsFinder = appContext.getBean(AllCollectionsFinder.class);
    view.displayCollections(allCollectionsFinder.execute().collections());
  }

}
