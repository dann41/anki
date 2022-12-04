package com.dann41.anki.cmd.intrastructure.presentation.cmd.usermenu;

import com.dann41.anki.cmd.intrastructure.presentation.cmd.CmdMenu;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.CmdMenuItem;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.CmdTools;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.core.BaseView;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.core.Navigator;

import java.util.List;

public class UserMenuView extends BaseView implements UserMenuContract.View {

  public static final String PLAY_DECK = "1";
  public static final String CREATE_DECK = "2";
  public static final String IMPORT_COLLECTION = "3";

  private final UserMenuContract.Presenter presenter;
  private final CmdTools cmdTools;

  public UserMenuView(Navigator navigator, UserMenuContract.Presenter presenter, CmdTools cmdTools) {
    super(navigator);
    this.presenter = presenter;
    this.cmdTools = cmdTools;
  }

  @Override
  public void show() {
    configurePresenter(presenter);

    System.out.println("--- ANKI Menu ---");
    displayMenu();
  }

  private void displayMenu() {
    var menu = new CmdMenu(
        "",
        List.of(
            CmdMenuItem.of(" 1. Play an existing deck", "1"),
            CmdMenuItem.of(" 2. Create a new deck", "2"),
            CmdMenuItem.of(" 3. Import collection from file", "3")
        ),
        "Choose an option: "
    );

    String option = cmdTools.printMenu(menu);

    switch (option) {
      case PLAY_DECK -> presenter.onPlayDeckSelected();
      case CREATE_DECK -> presenter.onCreateDeckSelected();
      case IMPORT_COLLECTION -> presenter.onImportCollectionSelected();
      default -> displayMenu();
    }
  }
}
