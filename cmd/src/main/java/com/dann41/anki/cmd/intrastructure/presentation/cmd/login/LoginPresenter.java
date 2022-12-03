package com.dann41.anki.cmd.intrastructure.presentation.cmd.login;

import com.dann41.anki.cmd.intrastructure.presentation.cmd.core.Navigator;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.model.session.SessionInteractor;
import com.dann41.anki.core.user.application.authenticator.UserAuthenticator;
import com.dann41.anki.core.user.application.userfinder.UserFinder;
import com.dann41.anki.core.user.domain.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginPresenter implements LoginContract.Presenter {
  private final UserAuthenticator authenticator;
  private final UserFinder userFinder;
  private final SessionInteractor sessionInteractor;
  private LoginContract.View view;
  private Navigator navigator;

  public LoginPresenter(UserAuthenticator authenticator, UserFinder userFinder, SessionInteractor sessionInteractor) {
    this.authenticator = authenticator;
    this.userFinder = userFinder;
    this.sessionInteractor = sessionInteractor;
  }

  @Override
  public void onAttachView(LoginContract.View view) {
    this.view = view;
  }

  @Override
  public void setNavigator(Navigator navigator) {
    this.navigator = navigator;
  }

  @Override
  public void onLoginSubmit(String username, String password) {
    try {
      sessionInteractor.login(username, password);
      view.showLoginSucceed();
      navigator.openUserMenuScreen();
    } catch (UserNotFoundException e) {
      view.showLoginFailed();
    }
  }
}
