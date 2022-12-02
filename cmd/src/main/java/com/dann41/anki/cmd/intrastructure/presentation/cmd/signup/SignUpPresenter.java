package com.dann41.anki.cmd.intrastructure.presentation.cmd.signup;

import com.dann41.anki.cmd.intrastructure.presentation.cmd.core.Navigator;
import com.dann41.anki.core.user.application.userregistrerer.RegisterUserCommand;
import com.dann41.anki.core.user.application.userregistrerer.UserRegisterer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignUpPresenter implements SignUpContract.Presenter {
  private SignUpContract.View view;
  private Navigator navigator;

  private final UserRegisterer registerer;
  private final PasswordEncoder passwordEncoder;

  public SignUpPresenter(UserRegisterer registerer, PasswordEncoder passwordEncoder) {
    this.registerer = registerer;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public void onAttachView(SignUpContract.View view) {
    this.view = view;
  }

  @Override
  public void setNavigator(Navigator navigator) {
    this.navigator = navigator;
  }

  @Override
  public void onSignupSubmit(String username, String password) {
    try {
      registerer.execute(new RegisterUserCommand(username, passwordEncoder.encode(password)));
      view.showSignupSucceed();
      navigator.openUserMenuScreen("");
    } catch (Exception e) {
      view.showSignupFailed();
    }
  }
}
