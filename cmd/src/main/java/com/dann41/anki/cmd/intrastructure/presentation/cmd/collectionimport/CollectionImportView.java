package com.dann41.anki.cmd.intrastructure.presentation.cmd.collectionimport;

import com.dann41.anki.cmd.intrastructure.presentation.cmd.CmdTools;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.core.BaseView;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.core.Navigator;

public class CollectionImportView extends BaseView implements CollectionImportContract.View {
  private final CollectionImportContract.Presenter presenter;
  private final CmdTools cmdTools;

  public CollectionImportView(Navigator navigator, CollectionImportContract.Presenter presenter, CmdTools cmdTools) {
    super(navigator);
    this.presenter = presenter;
    this.cmdTools = cmdTools;
  }

  @Override
  public void showCollectionCreated(String collectionId) {
    System.out.println("Collection created with id " + collectionId);
  }

  @Override
  public void showCollectionCreationError() {
    cmdTools.printError("Cannot create collection");
  }

  @Override
  public void show() {
    configurePresenter(presenter);

    System.out.println("-- Import collection --");
    displayCollectionImportDialog();
  }

  private void displayCollectionImportDialog() {
    System.out.println("Select file to import (e.g. core/src/main/resources/collections/cards.tsv): ");
    String resourceName = cmdTools.readLine();
    System.out.println("Name of the collection: ");
    String collectionName = cmdTools.readLine();
    presenter.createCollection(resourceName, collectionName);
  }
}
