package com.dann41.anki.cmd.intrastructure.presentation.cmd.core;

import com.dann41.anki.cmd.intrastructure.presentation.cmd.core.Navigator;

public interface Core {

  interface View {
    void show();
  }

  abstract class BaseView implements View {
    private Navigator navigator;

    protected BaseView(Navigator navigator) {
      this.navigator = navigator;
    }

    protected <T extends View> void configurePresenter(Presenter<T> presenter) {
        presenter.onAttachView((T) this);
        presenter.setNavigator(navigator);
    }
  }

  interface Presenter<T extends View> {
    void onAttachView(T view);
    void setNavigator(Navigator navigator);
  }

}
