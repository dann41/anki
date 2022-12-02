package com.dann41.anki.cmd.intrastructure.presentation.cmd.authentication;

import com.dann41.anki.cmd.intrastructure.presentation.cmd.core.Core;

public interface AuthenticationContract {
  interface View extends Core.View {

  }

  interface Presenter extends Core.Presenter<View> {
    void onLoginSelected();
    void onSignupSelected();
  }

}
