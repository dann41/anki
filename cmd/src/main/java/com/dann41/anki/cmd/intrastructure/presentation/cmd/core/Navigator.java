package com.dann41.anki.cmd.intrastructure.presentation.cmd.core;

public interface Navigator {

  void back();
  void openAuthenticationMenu();
  void openLoginScreen();
  void openSignUpScreen();
  void openUserMenuScreen();
  void openDeckSelectionScreen();
  void openDeckCreationScreen();
  void openCollectionImportScreen();
  void openDeckPlayerScreen();
}
