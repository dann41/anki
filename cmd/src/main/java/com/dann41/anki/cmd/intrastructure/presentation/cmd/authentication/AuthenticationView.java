package com.dann41.anki.cmd.intrastructure.presentation.cmd.authentication;

import com.dann41.anki.cmd.intrastructure.presentation.cmd.CmdMenu;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.CmdMenuItem;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.CmdTools;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.core.BaseView;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.core.Navigator;

import java.util.List;

public class AuthenticationView extends BaseView implements AuthenticationContract.View {

  public static final String SIGNUP_ACTION = "1";
  public static final String LOGIN_ACTION = "2";
  private final AuthenticationContract.Presenter presenter;
  private final CmdTools cmdTools;

  public AuthenticationView(Navigator navigator, AuthenticationContract.Presenter presenter, CmdTools cmdTools) {
    super(navigator);
    this.presenter = presenter;
    this.cmdTools = cmdTools;
  }

  @Override
  public void show() {
    configurePresenter(presenter);
    displayWelcome();
    displayAuthenticationDialog();
  }

  private void displayWelcome() {
    System.out.println("""
        Welcome to ANKI 2.0
        The best tool to study stuff
        """);
  }

  public void displayAuthenticationDialog() {
    var authMenu = new CmdMenu(
        "In order to play Anki you need to login",
        List.of(
            CmdMenuItem.of(" 1. Create a new user", "1"),
            CmdMenuItem.of(" 2. Login existing user", "2")
        ),
        "Choose an option: "
    );

    String action = cmdTools.printMenu(authMenu);

    switch (action) {
      case SIGNUP_ACTION -> presenter.onSignupSelected();
      case LOGIN_ACTION -> presenter.onLoginSelected();
    }
  }
}
