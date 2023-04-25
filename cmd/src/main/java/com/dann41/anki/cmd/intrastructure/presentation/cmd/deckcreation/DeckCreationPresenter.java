package com.dann41.anki.cmd.intrastructure.presentation.cmd.deckcreation;

import com.dann41.anki.cmd.intrastructure.presentation.cmd.ViewContext;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.core.BasePresenter;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.model.session.SessionInteractor;
import com.dann41.anki.core.cardcollection.application.allcollectionsfinder.AllCollectionsQuery;
import com.dann41.anki.core.deck.application.deckcreator.CollectionNotFoundException;
import com.dann41.anki.core.deck.deckcreator.CreateDeckCommand;
import com.dann41.anki.shared.application.CommandBus;
import com.dann41.anki.shared.application.QueryBus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeckCreationPresenter
    extends BasePresenter<DeckCreationScreen.View>
    implements DeckCreationScreen.Presenter {
  private final ViewContext viewContext = new ViewContext();
  private final SessionInteractor sessionInteractor;
  private final QueryBus queryBus;
  private final CommandBus commandBus;
  public DeckCreationPresenter(
      SessionInteractor sessionInteractor,
      QueryBus queryBus,
      CommandBus commandBus
  ) {
    this.sessionInteractor = sessionInteractor;
    this.queryBus = queryBus;
    this.commandBus = commandBus;
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
    var collections = queryBus.publish(new AllCollectionsQuery()).collections();
    view.displayCollections(collections);
  }

  @Override
  public void createDeck(String collectionId) {
    loadViewContext();

    String deckId = UUID.randomUUID().toString();

    try {
      commandBus.publish(
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
