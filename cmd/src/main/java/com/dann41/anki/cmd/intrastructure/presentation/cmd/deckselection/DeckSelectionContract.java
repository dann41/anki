package com.dann41.anki.cmd.intrastructure.presentation.cmd.deckselection;

import com.dann41.anki.cmd.intrastructure.presentation.cmd.core.Core;
import com.dann41.anki.core.deck.alldecksfinder.DeckSummary;

import java.util.List;

public interface DeckSelectionContract {
  interface View extends Core.View {
    void displayDecks(List<DeckSummary> decks);
  }

  interface Presenter extends Core.Presenter<View> {
    void loadDecks();
    void onDeckSelected(String deckId);
  }
}
