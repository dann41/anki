package com.dann41.anki.cmd.intrastructure.presentation.cmd.deckselection;

import com.dann41.anki.cmd.intrastructure.presentation.cmd.core.BasePresenter;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.model.session.SessionInteractor;
import com.dann41.anki.core.deck.alldecksfinder.MyDecksFinderQuery;
import com.dann41.anki.shared.application.QueryBus;
import org.springframework.stereotype.Service;

@Service
public class DeckSelectionPresenter
    extends BasePresenter<DeckSelectionContract.View>
    implements DeckSelectionContract.Presenter {

  private final SessionInteractor sessionInteractor;
  private final QueryBus queryBus;

  public DeckSelectionPresenter(
          SessionInteractor sessionInteractor,
          QueryBus queryBus
  ) {
    this.sessionInteractor = sessionInteractor;
    this.queryBus = queryBus;
  }

  @Override
  public void loadDecks() {
    var session = sessionInteractor.currentSession();
    if (session == null) {
      navigator.back();
      return;
    }

    var decksResponse = queryBus.publish(new MyDecksFinderQuery(session.userId()));
    view.displayDecks(decksResponse.decks());
  }

  @Override
  public void onDeckSelected(String deckId) {
    sessionInteractor.selectDeck(deckId);
    navigator.openDeckPlayerScreen();
  }
}
