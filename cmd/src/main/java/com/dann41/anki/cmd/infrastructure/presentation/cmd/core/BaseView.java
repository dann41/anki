package com.dann41.anki.cmd.infrastructure.presentation.cmd.core;

public abstract class BaseView implements Core.View {
  protected final Navigator navigator;

  protected BaseView(Navigator navigator) {
    this.navigator = navigator;
  }

  @SuppressWarnings("unchecked")
  protected <T extends Core.View> void configurePresenter(Core.Presenter<T> presenter) {
    presenter.onAttachView((T) this);
    presenter.setNavigator(navigator);
  }

  protected void goBack() {
    navigator.back();
  }
}
