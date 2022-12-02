package com.dann41.anki.cmd.intrastructure.presentation.cmd.authentication;

import com.dann41.anki.cmd.intrastructure.presentation.cmd.core.Navigator;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationPresenter implements AuthenticationContract.Presenter {
  private AuthenticationContract.View view;
  private Navigator navigator;

  public AuthenticationPresenter() {

  }

  @Override
  public void onAttachView(AuthenticationContract.View view) {
    this.view = view;
  }

  @Override
  public void setNavigator(Navigator navigator) {
    this.navigator = navigator;
  }

  @Override
  public void onLoginSelected() {
    navigator.openLoginScreen();
  }

  @Override
  public void onSignupSelected() {
    navigator.openSignUpScreen();
  }
}
