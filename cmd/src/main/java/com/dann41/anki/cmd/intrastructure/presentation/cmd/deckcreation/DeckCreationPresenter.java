package com.dann41.anki.cmd.intrastructure.presentation.cmd.deckcreation;

import com.dann41.anki.cmd.intrastructure.presentation.cmd.ViewContext;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.core.BasePresenter;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.model.session.SessionInteractor;
import com.dann41.anki.core.cardcollection.application.allcollectionsfinder.AllCollectionsFinder;
import com.dann41.anki.core.deck.application.deckcreator.CollectionNotFoundException;
import com.dann41.anki.core.deck.application.deckcreator.CreateDeckCommand;
import com.dann41.anki.core.deck.application.deckcreator.DeckCreator;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeckCreationPresenter
    extends BasePresenter<DeckCreationScreen.View>
    implements DeckCreationScreen.Presenter {
  private final AllCollectionsFinder allCollectionsFinder;
  private final DeckCreator deckCreator;
  private final ViewContext viewContext = new ViewContext();
  private final SessionInteractor sessionInteractor;
  public DeckCreationPresenter(
      AllCollectionsFinder allCollectionsFinder,
      DeckCreator deckCreator,
      SessionInteractor sessionInteractor
  ) {
    this.allCollectionsFinder = allCollectionsFinder;
    this.deckCreator = deckCreator;
    this.sessionInteractor = sessionInteractor;
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
  public void loadCollections() {
    loadViewContext();

    view.displayCollections(allCollectionsFinder.execute().collections());
  }

  @Override
  public void createDeck(String collectionId) {
    loadViewContext();

    String deckId = UUID.randomUUID().toString();

    try {
      deckCreator.execute(
          new CreateDeckCommand(
              deckId,
              viewContext.userId(),
              collectionId
          )
      );

      view.displayDeckCreated(deckId);
    } catch (CollectionNotFoundException exception) {
      view.displayError("Cannot find collection for id " + collectionId);
    }
    navigator.back();
  }

}
