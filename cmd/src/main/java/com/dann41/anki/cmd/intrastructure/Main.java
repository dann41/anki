package com.dann41.anki.cmd.intrastructure;

import com.dann41.anki.cmd.intrastructure.presentation.Starter;

public final class Main {

  public static void main(String[] args) {
    String deckId = "arts";
    AppContext appContext = new AppContext();
    appContext.get(Starter.class).start(deckId);
  }
}
