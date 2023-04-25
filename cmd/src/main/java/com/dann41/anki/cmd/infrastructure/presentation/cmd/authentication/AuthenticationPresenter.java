package com.dann41.anki.cmd.infrastructure.presentation.cmd.authentication;

import com.dann41.anki.cmd.infrastructure.presentation.cmd.core.BasePresenter;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationPresenter
    extends BasePresenter<AuthenticationContract.View>
    implements AuthenticationContract.Presenter {

  @Override
  public void onLoginSelected() {
    navigator.openLoginScreen();
  }

  @Override
  public void onSignupSelected() {
    navigator.openSignUpScreen();
  }
}
