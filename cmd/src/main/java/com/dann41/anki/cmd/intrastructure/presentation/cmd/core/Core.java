package com.dann41.anki.cmd.intrastructure.presentation.cmd.core;

public interface Core {

  interface View {
    void show();
  }

  interface Presenter<T extends View> {
    void onAttachView(T view);
    void setNavigator(Navigator navigator);
  }

}
