package com.dann41.anki.cmd.intrastructure.presentation;

import com.dann41.anki.cmd.intrastructure.presentation.cmd.ConsoleView;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class Starter {

  private final ApplicationContext appContext;

  public Starter(ApplicationContext appContext) {
    this.appContext = appContext;
  }

  public void start(String deckId, String collectionId) {
    // TODO move presenter and view to AppContext
    Presenter presenter = new InteractivePresenter(deckId, collectionId, appContext);
    View view = new ConsoleView(presenter);
    view.show();
  }
}
