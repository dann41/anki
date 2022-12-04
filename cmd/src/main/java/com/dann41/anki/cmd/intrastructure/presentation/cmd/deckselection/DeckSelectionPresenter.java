package com.dann41.anki.cmd.intrastructure.presentation.cmd.deckselection;

import com.dann41.anki.cmd.intrastructure.presentation.cmd.core.Navigator;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.model.session.SessionInteractor;
import com.dann41.anki.core.deck.application.alldecksfinder.MyDecksFinder;
import com.dann41.anki.core.deck.application.alldecksfinder.MyDecksFinderQuery;
import org.springframework.stereotype.Service;

@Service
public class DeckSelectionPresenter implements DeckSelectionContract.Presenter {

  private final MyDecksFinder myDecksFinder;
  private final SessionInteractor sessionInteractor;
  private DeckSelectionContract.View view;
  private Navigator navigator;

  public DeckSelectionPresenter(MyDecksFinder myDecksFinder, SessionInteractor sessionInteractor) {
    this.myDecksFinder = myDecksFinder;
    this.sessionInteractor = sessionInteractor;
  }

  @Override
  public void onAttachView(DeckSelectionContract.View view) {
    this.view = view;
  }

  @Override
  public void setNavigator(Navigator navigator) {
    this.navigator = navigator;
  }

  @Override
  public void loadDecks() {
    var session = sessionInteractor.currentSession();
    if (session == null) {
      navigator.back();
      return;
    }

    var decksResponse = myDecksFinder.execute(new MyDecksFinderQuery(session.userId()));
    view.displayDecks(decksResponse.decks());
  }

  @Override
  public void onDeckSelected(String deckId) {
    sessionInteractor.selectDeck(deckId);
    navigator.openDeckPlayerScreen();
  }
}
