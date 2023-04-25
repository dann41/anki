package com.dann41.anki.cmd.infrastructure.presentation.cmd.login;

import com.dann41.anki.cmd.infrastructure.presentation.cmd.core.BasePresenter;
import com.dann41.anki.cmd.infrastructure.presentation.cmd.model.session.SessionInteractor;
import com.dann41.anki.core.user.domain.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginPresenter
    extends BasePresenter<LoginContract.View>
    implements LoginContract.Presenter {
  private final SessionInteractor sessionInteractor;

  public LoginPresenter(SessionInteractor sessionInteractor) {
    this.sessionInteractor = sessionInteractor;
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
