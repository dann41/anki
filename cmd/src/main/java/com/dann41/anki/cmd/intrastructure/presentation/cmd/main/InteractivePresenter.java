package com.dann41.anki.cmd.intrastructure.presentation.cmd.main;

import com.dann41.anki.cmd.intrastructure.presentation.cmd.AnkiState;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.core.Navigator;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.ViewContext;
import com.dann41.anki.cmd.intrastructure.services.FileCollectionImporter;
import com.dann41.anki.core.cardcollection.application.allcollectionsfinder.AllCollectionsFinder;
import com.dann41.anki.core.deck.application.alldecksfinder.MyDecksFinder;
import com.dann41.anki.core.deck.application.alldecksfinder.MyDecksFinderQuery;
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
import com.dann41.anki.core.user.application.authenticator.UserAuthenticator;
import com.dann41.anki.core.user.application.authenticator.UserAuthenticatorCommand;
import com.dann41.anki.core.user.application.userfinder.UserFinder;
import com.dann41.anki.core.user.application.userfinder.UserResponse;
import com.dann41.anki.core.user.application.userregistrerer.RegisterUserCommand;
import com.dann41.anki.core.user.application.userregistrerer.UserRegistrerer;
import com.dann41.anki.core.user.domain.UserNotFoundException;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
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
  private MainContract.View view;
  private Navigator navigator;

  public InteractivePresenter(ApplicationContext appContext) {
    // TODO inject use cases instead of AppContext
    this.appContext = appContext;
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
  public void register(String username, String password) {
    var registrerer = appContext.getBean(UserRegistrerer.class);
    var hasher = appContext.getBean(PasswordEncoder.class);
    try {
      registrerer.execute(new RegisterUserCommand(username, hasher.encode(password)));
      view.displayMessage("User created. You can proceed to login");
      view.displayLogin();
    } catch (Exception e) {
      view.displayError(e.getMessage());
      navigator.openAuthenticationMenu();
    }
  }

  @Override
  public void login(String username, String password) {
    UserAuthenticator authenticator = appContext.getBean(UserAuthenticator.class);
    try {
      authenticator.execute(new UserAuthenticatorCommand(username, password));

      UserFinder userFinder = appContext.getBean(UserFinder.class);
      UserResponse user = userFinder.execute(username);
      this.viewContext.login(user.id(), user.username());

      view.displayLoginSucceed();
      view.displayMainMenu();
    } catch (UserNotFoundException e) {
      view.displayError("Invalid login. The user does not exist or the password is invalid");
      navigator.openAuthenticationMenu();
    }
  }

  @Override
  public void playDeck(String deckId) {
    startSession(deckId);
    displayState();
    displayNextCard();
  }

  @Override
  public void createDeck(String collectionId) {
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
      view.displayMainMenu();
    } catch (CollectionNotFoundException exception) {
      view.displayError("Cannot find collection for id " + collectionId);
      view.displayMainMenu();
    }
  }

  private void startSession(String deckId) {
    var sessionStarter = appContext.getBean(SessionStarter.class);
    try {
      sessionStarter.execute(new StartSessionCommand(deckId, viewContext.userId()));
      viewContext.playDeck(deckId);
    } catch (DeckNotFoundException e) {
      view.displayError("Cannot find deck for id " + deckId);
      view.displayMainMenu();
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
    view.displayMainMenu();
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
  public void loadDecks() {
    MyDecksFinder myDecksFinder = appContext.getBean(MyDecksFinder.class);
    view.displayDecks(myDecksFinder.execute(new MyDecksFinderQuery(viewContext.userId())).decks());
  }

  @Override
  public void loadCollections() {
    var allCollectionsFinder = appContext.getBean(AllCollectionsFinder.class);
    view.displayCollections(allCollectionsFinder.execute().collections());
  }

  @Override
  public void createCollection(String resourceName, String collectionName) {
    var importer = appContext.getBean(FileCollectionImporter.class);
    var collectionId = UUID.randomUUID().toString();
    try {
      importer.importCollection(resourceName, collectionId, collectionName);
      view.displayMessage("Collection created with id " + collectionId);
      view.displayMainMenu();
    } catch (RuntimeException e) {
      view.displayError("Cannot create collection. Reason: " + e.getMessage());
      view.displayMainMenu();
    }
  }
}
