package com.dann41.anki.cmd.intrastructure.presentation.cmd;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class Starter {

  private final ApplicationContext appContext;

  public Starter(ApplicationContext appContext) {
    this.appContext = appContext;
  }

  public void start() {
    // TODO move presenter and view to AppContext
    Presenter presenter = new InteractivePresenter(appContext);
    View view = new ConsoleView(presenter);
    view.show();
  }
}
