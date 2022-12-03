package com.dann41.anki.cmd.intrastructure.presentation.cmd.collectionimport;

import com.dann41.anki.cmd.intrastructure.presentation.cmd.core.Navigator;
import com.dann41.anki.cmd.intrastructure.services.FileCollectionImporter;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CollectionImportPresenter implements CollectionImportContract.Presenter {
  private final FileCollectionImporter importer;
  private CollectionImportContract.View view;
  private Navigator navigator;

  public CollectionImportPresenter(FileCollectionImporter importer) {
    this.importer = importer;
  }

  @Override
  public void createCollection(String resourceName, String collectionName) {
    var collectionId = UUID.randomUUID().toString();

    try {
      importer.importCollection(resourceName, collectionId, collectionName);
      view.showCollectionCreated(collectionId);
    } catch (RuntimeException e) {
      view.showCollectionCreationError();
    }

    navigator.back();
  }

  @Override
  public void onAttachView(CollectionImportContract.View view) {
    this.view = view;
  }

  @Override
  public void setNavigator(Navigator navigator) {
    this.navigator = navigator;
  }
}
