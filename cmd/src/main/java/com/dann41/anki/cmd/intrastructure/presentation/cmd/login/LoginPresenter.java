package com.dann41.anki.cmd.intrastructure.presentation.cmd.login;

import com.dann41.anki.cmd.intrastructure.presentation.cmd.RetriesExhaustedException;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.Retryer;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.core.Navigator;
import com.dann41.anki.core.user.application.authenticator.UserAuthenticator;
import com.dann41.anki.core.user.application.authenticator.UserAuthenticatorCommand;
import com.dann41.anki.core.user.application.userfinder.UserFinder;
import com.dann41.anki.core.user.application.userfinder.UserResponse;
import com.dann41.anki.core.user.domain.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginPresenter implements LoginContract.Presenter {
  private static final int MAX_TRIES = 3;
  private final UserAuthenticator authenticator;
  private final UserFinder userFinder;
  private LoginContract.View view;
  private Navigator navigator;

  private Retryer retryer = new Retryer(MAX_TRIES);


  public LoginPresenter(UserAuthenticator authenticator, UserFinder userFinder) {
    this.authenticator = authenticator;
    this.userFinder = userFinder;
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
      authenticator.execute(new UserAuthenticatorCommand(username, password));
      UserResponse user = userFinder.execute(username);
      view.showLoginSucceed();
      retryer.reset();
      navigator.openUserMenuScreen(user.id());
    } catch (UserNotFoundException e) {
      try {
        retryer.retry(() -> view.showLoginFailed());
      } catch (RetriesExhaustedException e2) {
        retryer.reset();
        navigator.openAuthenticationMenu();
      }
    }
  }
}
