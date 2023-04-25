package com.dann41.anki.cmd.infrastructure.presentation.cmd.signup;

import com.dann41.anki.cmd.infrastructure.presentation.cmd.core.Core;

public interface SignUpContract {

  interface View extends Core.View {
    void showSignupFailed();
    void showSignupSucceed();
  }

  interface Presenter extends Core.Presenter<View> {
    void onSignupSubmit(String username, String password);
  }
}
