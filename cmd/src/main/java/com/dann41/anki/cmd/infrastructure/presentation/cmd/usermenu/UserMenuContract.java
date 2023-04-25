package com.dann41.anki.cmd.infrastructure.presentation.cmd.usermenu;

import com.dann41.anki.cmd.infrastructure.presentation.cmd.core.Core;

public interface UserMenuContract {

  interface View extends Core.View {

  }

  interface Presenter extends Core.Presenter<View> {

    void onPlayDeckSelected();

    void onCreateDeckSelected();

    void onImportCollectionSelected();
  }

}
