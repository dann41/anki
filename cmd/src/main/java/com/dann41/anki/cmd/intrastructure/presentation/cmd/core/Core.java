package com.dann41.anki.cmd.intrastructure.presentation.cmd.core;

public interface Core {

  interface View {
    void show();
  }

  abstract class BaseView implements View {
    protected final Navigator navigator;

    protected BaseView(Navigator navigator) {
      this.navigator = navigator;
    }

    protected <T extends View> void configurePresenter(Presenter<T> presenter) {
        presenter.onAttachView((T) this);
        presenter.setNavigator(navigator);
    }

    protected void goBack() {
      navigator.back();
    }
  }

  interface Presenter<T extends View> {
    void onAttachView(T view);
    void setNavigator(Navigator navigator);
  }

}
