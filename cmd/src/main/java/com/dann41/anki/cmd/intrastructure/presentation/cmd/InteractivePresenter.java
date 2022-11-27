package com.dann41.anki.cmd.intrastructure.presentation.cmd;

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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class InteractivePresenter implements Presenter {
  private final ApplicationContext appContext;
  private final ViewContext viewContext = new ViewContext();
  private View view;
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
    view.displayAuthenticationDialog();
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
      view.displayAuthenticationDialog();
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
      view.displayAuthenticationDialog();
    }
  }

  @Override
  public void playDeck(String deckId) {
    this.viewContext.playDeck(deckId);
    startSession();
    displayState();
    displayNextCard();
  }

  @Override
  public void createDeck(String collectionId) {
    this.viewContext.selectCollection(collectionId);
    this.viewContext.playDeck(UUID.randomUUID().toString());
    startSession();
  }

  private void startSession() {
    var sessionStarter = appContext.getBean(SessionStarter.class);
    try {
      sessionStarter.execute(new StartSessionCommand(viewContext.currentDeckId(), viewContext.userId()));
    } catch (DeckNotFoundException e) {
      if (!viewContext.hasCollectionSelected()) {
        view.displayError("You must specify a collection");
        view.displayMainMenu();
        return;
      }

      var deckCreator = appContext.getBean(DeckCreator.class);
      try {
        deckCreator.execute(new CreateDeckCommand(
            viewContext.currentDeckId(),
            viewContext.userId(),
            viewContext.selectedCollectionId()));

        sessionStarter.execute(new StartSessionCommand(
            viewContext.currentDeckId(),
            viewContext.userId()
        ));

        view.displayMessage("Deck created with id " + viewContext.currentDeckId());
        view.displayMainMenu();
      } catch (CollectionNotFoundException exception) {
        view.displayError("Cannot find collection for id " + viewContext.selectedCollectionId());
        view.displayMainMenu();
      }
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
  }

  private CardResponse getNextCard() {
    CardPicker cardPicker = appContext.getBean(CardPicker.class);
    return cardPicker.execute(new CardPickerQuery(viewContext.currentDeckId(), viewContext.userId()));
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
