package com.dann41.anki.cmd.intrastructure.presentation.cmd.core;

import com.dann41.anki.cmd.intrastructure.presentation.cmd.CmdTools;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.authentication.AuthenticationContract;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.authentication.AuthenticationView;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.login.LoginContract;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.login.LoginView;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.main.ConsoleView;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.main.MainContract;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CmdNavigator implements Navigator {

  private final CmdTools cmdTools;
  private final AuthenticationContract.Presenter authenticationPresenter;
  private final MainContract.Presenter mainPresenter;
  private final MainContract.View mainView;
  private final LoginContract.Presenter loginPresenter;

  public CmdNavigator(CmdTools cmdTools,
                      AuthenticationContract.Presenter authenticationPresenter,
                      MainContract.Presenter mainPresenter,
                      LoginContract.Presenter loginPresenter) {
    this.cmdTools = cmdTools;
    this.authenticationPresenter = authenticationPresenter;
    this.mainPresenter = mainPresenter;
    this.loginPresenter = loginPresenter;

    this.mainView = new ConsoleView(this, mainPresenter);
  }

  @Override
  public void openAuthenticationMenu() {
    new AuthenticationView(this, authenticationPresenter, cmdTools).show();
  }

  @Override
  public void openLoginScreen() {
    var loginView = new LoginView(this, loginPresenter, cmdTools);
    loginView.show();
  }

  @Override
  public void openSignUpScreen() {
    mainView.show();
    mainView.displaySignUp();
  }

  @Override
  public void openUserMenuScreen(String userId) {
    mainView.show();
    mainView.displayMainMenu();
  }

  @Override
  public void openDeckSelectionScreen(String userId) {
    mainView.show();
    mainView.displayDecks(Collections.emptyList());
  }

  @Override
  public void openDeckCreationScreen(String userId) {
    mainView.show();
    mainView.displayCollections(Collections.emptyList());
  }

  @Override
  public void openCollectionImportScreen(String userId) {
    mainView.show();
    // TODO
  }
}
