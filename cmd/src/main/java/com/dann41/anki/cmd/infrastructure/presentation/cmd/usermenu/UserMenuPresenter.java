package com.dann41.anki.cmd.infrastructure.presentation.cmd.usermenu;

import com.dann41.anki.cmd.infrastructure.presentation.cmd.core.Navigator;
import org.springframework.stereotype.Service;

@Service
public class UserMenuPresenter implements UserMenuContract.Presenter {
  private Navigator navigator;

  @Override
  public void onAttachView(UserMenuContract.View view) {

  }

  @Override
  public void setNavigator(Navigator navigator) {
    this.navigator = navigator;
  }

  @Override
  public void onPlayDeckSelected() {
    navigator.openDeckSelectionScreen();
  }

  @Override
  public void onCreateDeckSelected() {
    navigator.openDeckCreationScreen();
  }

  @Override
  public void onImportCollectionSelected() {
    navigator.openCollectionImportScreen();
  }
}
