package com.dann41.anki.cmd.intrastructure.presentation.cmd.core;

public interface Navigator {

  void back();
  void openAuthenticationMenu();
  void openLoginScreen();
  void openSignUpScreen();
  void openUserMenuScreen(String userId);
  void openDeckSelectionScreen(String userId);
  void openDeckCreationScreen(String userId);
  void openCollectionImportScreen(String userId);
}
