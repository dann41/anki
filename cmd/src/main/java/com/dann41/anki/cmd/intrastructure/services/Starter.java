package com.dann41.anki.cmd.intrastructure.services;

import com.dann41.anki.cmd.intrastructure.presentation.cmd.core.Navigator;
import org.springframework.context.ApplicationContext;

public class Starter {

  private final ApplicationContext appContext;

  public Starter(ApplicationContext appContext) {
    this.appContext = appContext;
  }

  public void start() {
    // TODO move presenter and view to AppContext
    var navigator = appContext.getBean(Navigator.class);
    navigator.openAuthenticationMenu();

    /*MainPresenter presenter = new InteractivePresenter(appContext);
    MainView view = new ConsoleView(presenter);
    view.show();*/
  }
}
