package com.dann41.anki.cmd.intrastructure.presentation.cmd.login;

import com.dann41.anki.cmd.intrastructure.presentation.cmd.CmdTools;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.core.Core;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.core.Navigator;

public class LoginView extends Core.BaseView implements LoginContract.View {
  private final LoginContract.Presenter presenter;
  private final CmdTools cmdTools;

  public LoginView(Navigator navigator, LoginContract.Presenter presenter, CmdTools cmdTools) {
    super(navigator);
    this.presenter = presenter;
    this.cmdTools = cmdTools;
  }

  @Override
  public void show() {
    configurePresenter(presenter);
    requestLoginCredentials(true);
  }

  private void requestLoginCredentials(boolean showTitle) {
    if (showTitle) {
      System.out.println("--- LOGIN ---");
    }
    System.out.print("Username: ");
    String username = cmdTools.readLine();
    System.out.print("Password: ");
    String password = cmdTools.readLine();

    presenter.onLoginSubmit(username, password);
  }

  @Override
  public void showLoginSucceed() {
    cmdTools.printInfo("Login succeed");
  }

  @Override
  public void showLoginFailed() {
    cmdTools.printError("Invalid login. The user does not exist or the password is invalid");
    requestLoginCredentials(false);
  }
}
