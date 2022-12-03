package com.dann41.anki.cmd.intrastructure.presentation.cmd.login;

import com.dann41.anki.cmd.intrastructure.presentation.cmd.CmdTools;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.RetriesExhaustedException;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.Retryer;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.core.Core;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.core.Navigator;

public class LoginView extends Core.BaseView implements LoginContract.View {
  private static final int MAX_TRIES = 3;
  private final LoginContract.Presenter presenter;
  private final CmdTools cmdTools;
  private final Retryer retryer;


  public LoginView(Navigator navigator, LoginContract.Presenter presenter, CmdTools cmdTools) {
    super(navigator);
    this.presenter = presenter;
    this.cmdTools = cmdTools;
    this.retryer = new Retryer(MAX_TRIES);
  }

  @Override
  public void show() {
    configurePresenter(presenter);
    System.out.println("--- LOGIN ---");
    requestLoginCredentials();
  }

  private void requestLoginCredentials() {
    try {
      retryer.retry(() -> {
        System.out.print("Username: ");
        String username = cmdTools.readLine();
        System.out.print("Password: ");
        String password = cmdTools.readLine();

        presenter.onLoginSubmit(username, password);
      });
    } catch (RetriesExhaustedException e) {
      cmdTools.printError("Too may tries... back to auth menu");
      goBack();
    }
  }

  @Override
  public void showLoginSucceed() {
    cmdTools.printInfo("Login succeed");
  }

  @Override
  public void showLoginFailed() {
    cmdTools.printError("Invalid login. The user does not exist or the password is invalid");
    requestLoginCredentials();
  }
}
