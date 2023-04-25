package com.dann41.anki.cmd.infrastructure.presentation.cmd.authentication;

import com.dann41.anki.cmd.infrastructure.presentation.cmd.core.Core;

public interface AuthenticationContract {
  interface View extends Core.View {

  }

  interface Presenter extends Core.Presenter<View> {
    void onLoginSelected();
    void onSignupSelected();
  }

}
