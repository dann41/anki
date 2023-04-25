package com.dann41.anki.cmd.infrastructure.presentation.cmd.collectionimport;

import com.dann41.anki.cmd.infrastructure.presentation.cmd.core.Core;

public interface CollectionImportContract {

  interface View extends Core.View {
    void showCollectionCreated(String collectionId);

    void showCollectionCreationError();
  }

  interface Presenter extends Core.Presenter<View> {
    void createCollection(String resourceName, String collectionName);
  }

}
