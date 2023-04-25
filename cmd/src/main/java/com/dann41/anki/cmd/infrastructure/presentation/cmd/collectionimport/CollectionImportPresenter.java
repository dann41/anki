package com.dann41.anki.cmd.infrastructure.presentation.cmd.collectionimport;

import com.dann41.anki.cmd.infrastructure.presentation.cmd.core.BasePresenter;
import com.dann41.anki.cmd.infrastructure.services.FileCollectionImporter;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CollectionImportPresenter
    extends BasePresenter<CollectionImportContract.View>
    implements CollectionImportContract.Presenter {
  private final FileCollectionImporter importer;
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
}
