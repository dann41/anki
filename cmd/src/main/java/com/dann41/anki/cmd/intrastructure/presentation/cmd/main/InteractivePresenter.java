package com.dann41.anki.cmd.intrastructure.presentation.cmd.main;

import com.dann41.anki.cmd.intrastructure.presentation.cmd.ViewContext;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.core.Navigator;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.model.session.SessionInteractor;
import com.dann41.anki.core.cardcollection.application.allcollectionsfinder.AllCollectionsFinder;
import com.dann41.anki.core.deck.application.deckcreator.CollectionNotFoundException;
import com.dann41.anki.core.deck.application.deckcreator.CreateDeckCommand;
import com.dann41.anki.core.deck.application.deckcreator.DeckCreator;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InteractivePresenter implements MainContract.Presenter {
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

  @Override
  public void loadCollections() {
    loadViewContext();

    var allCollectionsFinder = appContext.getBean(AllCollectionsFinder.class);
    view.displayCollections(allCollectionsFinder.execute().collections());
  }

}
