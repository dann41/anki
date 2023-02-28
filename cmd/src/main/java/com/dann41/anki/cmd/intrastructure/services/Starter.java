package com.dann41.anki.cmd.intrastructure.services;

import com.dann41.anki.cmd.intrastructure.presentation.cmd.core.Navigator;

public class Starter {

  private final Navigator navigator;

  public Starter(Navigator navigator) {
    this.navigator = navigator;
  }

  public void start() {
    navigator.openAuthenticationMenu();
  }
}
