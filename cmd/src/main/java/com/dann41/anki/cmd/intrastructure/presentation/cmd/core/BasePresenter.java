package com.dann41.anki.cmd.intrastructure.presentation.cmd.core;

public abstract class BasePresenter<T extends Core.View> implements Core.Presenter<T> {
  protected Navigator navigator;
  protected T view;

  @Override
  public void onAttachView(T view) {
    this.view = view;
  }

  @Override
  public void setNavigator(Navigator navigator) {
    this.navigator = navigator;
  }
}
