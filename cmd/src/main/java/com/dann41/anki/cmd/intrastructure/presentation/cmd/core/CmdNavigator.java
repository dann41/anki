package com.dann41.anki.cmd.intrastructure.presentation.cmd.core;

import com.dann41.anki.cmd.intrastructure.presentation.cmd.CmdTools;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.authentication.AuthenticationContract;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.authentication.AuthenticationView;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.login.LoginContract;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.login.LoginView;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.main.ConsoleView;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.main.MainContract;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.signup.SignUpContract;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.signup.SignUpView;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.EmptyStackException;
import java.util.Stack;

@Service
public class CmdNavigator implements Navigator {

  private final CmdTools cmdTools;
  private final AuthenticationContract.Presenter authenticationPresenter;
  private final MainContract.Presenter mainPresenter;
  private final MainContract.View mainView;
  private final LoginContract.Presenter loginPresenter;
  private final SignUpContract.Presenter signUpPresenter;

  private Core.View currentView;
  private final Stack<Core.View> viewStack = new Stack<>();

  public CmdNavigator(CmdTools cmdTools,
                      AuthenticationContract.Presenter authenticationPresenter,
                      MainContract.Presenter mainPresenter,
                      LoginContract.Presenter loginPresenter,
                      SignUpContract.Presenter signUpPresenter) {
    this.cmdTools = cmdTools;
    this.authenticationPresenter = authenticationPresenter;
    this.mainPresenter = mainPresenter;
    this.loginPresenter = loginPresenter;
    this.signUpPresenter = signUpPresenter;

    this.mainView = new ConsoleView(this, mainPresenter);
  }

  @Override
  public void back() {
    try {
      var view = viewStack.pop();
      currentView = view;
      view.show();
    } catch (EmptyStackException e) {

    }
  }

  @Override
  public void openAuthenticationMenu() {
    var view = new AuthenticationView(this, authenticationPresenter, cmdTools);
    showView(view);
  }

  @Override
  public void openLoginScreen() {
    var view = new LoginView(this, loginPresenter, cmdTools);
    showView(view);
  }

  @Override
  public void openSignUpScreen() {
    var view = new SignUpView(this, signUpPresenter, cmdTools);
    showView(view);
  }

  @Override
  public void openUserMenuScreen(String userId) {
    showView(mainView);
    mainView.displayMainMenu();
  }

  @Override
  public void openDeckSelectionScreen(String userId) {
    showView(mainView);
    mainView.displayDecks(Collections.emptyList());
  }

  @Override
  public void openDeckCreationScreen(String userId) {
    showView(mainView);
    mainView.displayCollections(Collections.emptyList());
  }

  @Override
  public void openCollectionImportScreen(String userId) {
    showView(mainView);
  }

  private void showView(Core.View view) {
    if (currentView != null) {
      viewStack.push(currentView);
    }
    currentView = view;
    view.show();
  }
}
