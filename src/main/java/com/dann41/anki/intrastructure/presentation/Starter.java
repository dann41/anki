package com.dann41.anki.intrastructure.presentation;

import com.dann41.anki.intrastructure.AppContext;
import com.dann41.anki.intrastructure.presentation.cmd.ConsoleView;

public class Starter {

  private final AppContext appContext;

  public Starter(AppContext appContext) {
    this.appContext = appContext;
  }

  public void start(String deckId) {
    // TODO move presenter and view to AppContext
    Presenter presenter = new InteractivePresenter(deckId, appContext);
    View view = new ConsoleView(presenter);
    view.show();
  }
}
