package com.dann41.anki.cmd.intrastructure.presentation.cmd;

import com.dann41.anki.core.application.collection.allcollectionsfinder.AllCollectionsFinder;
import com.dann41.anki.core.application.deck.alldecksfinder.AllDecksFinder;
import com.dann41.anki.core.application.deck.cardpicker.CardPicker;
import com.dann41.anki.core.application.deck.cardpicker.CardResponse;
import com.dann41.anki.core.application.deck.cardsolver.CardSolver;
import com.dann41.anki.core.application.deck.cardsolver.SolveCardCommand;
import com.dann41.anki.core.application.deck.deckcreator.CollectionNotFoundException;
import com.dann41.anki.core.application.deck.deckcreator.CreateDeckCommand;
import com.dann41.anki.core.application.deck.deckcreator.DeckCreator;
import com.dann41.anki.core.application.deck.sessionstarter.SessionStarter;
import com.dann41.anki.core.application.deck.sessionstarter.StartSessionCommand;
import com.dann41.anki.core.application.deck.statefinder.StateFinder;
import com.dann41.anki.core.application.deck.statefinder.StateResponse;
import com.dann41.anki.core.domain.deck.DeckNotFoundException;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class InteractivePresenter implements Presenter {
  private final ApplicationContext appContext;
  private View view;
  private String deckId;
  private String collectionId;
  private static final Map<String, String> BOX_MAPPER = new HashMap<>();

  static {
    BOX_MAPPER.put("g", "green");
    BOX_MAPPER.put("o", "orange");
    BOX_MAPPER.put("r", "red");
  }

  public InteractivePresenter(ApplicationContext appContext) {
    // TODO inject use cases instead of AppContext
    this.appContext = appContext;
  }

  @Override
  public void onViewShown(View view) {
    this.view = view;
    onStart();
  }

  public void onStart() {
    view.displayWelcome();
    view.displayMainMenu();
  }

  @Override
  public void playDeck(String deckId) {
    this.deckId = deckId;
    startSession();
    displayState();
    displayNextCard();
  }

  @Override
  public void createDeck(String collectionId) {
    this.deckId = UUID.randomUUID().toString();
    this.collectionId = collectionId;
    startSession();
  }

  private void startSession() {
    var sessionStarter = appContext.getBean(SessionStarter.class);
    try {
      sessionStarter.execute(new StartSessionCommand(deckId));
    } catch (DeckNotFoundException e) {
      if (collectionId == null) {
        view.displayError("Cannot find deckId for id " + e.deckId().toString());
        view.displayMainMenu();
        return;
      }

      var deckCreator = appContext.getBean(DeckCreator.class);
      try {
        deckCreator.execute(new CreateDeckCommand(deckId, collectionId));
        sessionStarter.execute(new StartSessionCommand(deckId));
      } catch (CollectionNotFoundException exception) {
        view.displayError("Cannot find collectionId for id " + e.deckId().toString());
        view.displayMainMenu();
      }
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

  @Override
  public void loadDecks() {
    AllDecksFinder allDecksFinder = appContext.getBean(AllDecksFinder.class);
    view.displayDecks(allDecksFinder.execute().decks());
  }

  @Override
  public void loadCollections() {
    var allCollectionsFinder = appContext.getBean(AllCollectionsFinder.class);
    view.displayCollections(allCollectionsFinder.execute().collections());
  }
}
