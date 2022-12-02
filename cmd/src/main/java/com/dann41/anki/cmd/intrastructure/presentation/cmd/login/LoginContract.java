package com.dann41.anki.cmd.intrastructure.presentation.cmd.login;

import com.dann41.anki.cmd.intrastructure.presentation.cmd.core.Core;

public interface LoginContract {

  interface View extends Core.View {
    void showLoginSucceed();

    void showLoginFailed();
  }

  interface Presenter extends Core.Presenter<View> {
    void onLoginSubmit(String username, String password);
  }

}
