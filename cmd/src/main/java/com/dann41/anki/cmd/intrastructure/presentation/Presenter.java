package com.dann41.anki.cmd.intrastructure.presentation;

public interface Presenter {

  void onViewShown(View view);

  void solveCard(String cardId, String box);
}
