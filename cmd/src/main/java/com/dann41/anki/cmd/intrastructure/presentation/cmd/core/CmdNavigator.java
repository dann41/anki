package com.dann41.anki.cmd.intrastructure.presentation.cmd.core;

import com.dann41.anki.cmd.intrastructure.presentation.cmd.CmdTools;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.authentication.AuthenticationContract;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.authentication.AuthenticationView;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.collectionimport.CollectionImportContract;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.collectionimport.CollectionImportView;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.deckplayer.DeckPlayerContract;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.deckplayer.DeckPlayerView;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.deckselection.DeckSelectionContract;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.deckselection.DeckSelectionView;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.login.LoginContract;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.login.LoginView;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.deckcreation.DeckCreationView;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.deckcreation.DeckCreationScreen;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.signup.SignUpContract;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.signup.SignUpView;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.usermenu.UserMenuContract;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.usermenu.UserMenuView;
import org.springframework.stereotype.Service;

import java.util.EmptyStackException;
import java.util.Stack;

@Service
public class CmdNavigator implements Navigator {

  private final CmdTools cmdTools;
  private final AuthenticationContract.Presenter authenticationPresenter;
  private final DeckCreationScreen.Presenter mainPresenter;
  private final LoginContract.Presenter loginPresenter;
  private final SignUpContract.Presenter signUpPresenter;
  private final UserMenuContract.Presenter userMenuPresenter;
  private final CollectionImportContract.Presenter collectionImportPresenter;
  private final DeckSelectionContract.Presenter deckSelectionPresenter;
  private final DeckPlayerContract.Presenter deckPlayerPresenter;

  private Core.View currentView;
  private final Stack<Core.View> viewStack = new Stack<>();

  public CmdNavigator(CmdTools cmdTools,
                      AuthenticationContract.Presenter authenticationPresenter,
                      DeckCreationScreen.Presenter mainPresenter,
                      LoginContract.Presenter loginPresenter,
                      SignUpContract.Presenter signUpPresenter,
                      UserMenuContract.Presenter userMenuPresenter,
                      CollectionImportContract.Presenter collectionImportPresenter,
                      DeckSelectionContract.Presenter deckSelectionPresenter,
                      DeckPlayerContract.Presenter deckPlayerPresenter) {
    this.cmdTools = cmdTools;
    this.authenticationPresenter = authenticationPresenter;
    this.mainPresenter = mainPresenter;
    this.loginPresenter = loginPresenter;
    this.signUpPresenter = signUpPresenter;
    this.userMenuPresenter = userMenuPresenter;
    this.collectionImportPresenter = collectionImportPresenter;
    this.deckSelectionPresenter = deckSelectionPresenter;
    this.deckPlayerPresenter = deckPlayerPresenter;
  }

  @Override
  public void back() {
    try {
      var view = viewStack.pop();
      currentView = view;
      view.show();
    } catch (EmptyStackException e) {

    }
  }

  @Override
  public void openAuthenticationMenu() {
    var view = new AuthenticationView(this, authenticationPresenter, cmdTools);
    showView(view);
  }

  @Override
  public void openLoginScreen() {
    var view = new LoginView(this, loginPresenter, cmdTools);
    showView(view);
  }

  @Override
  public void openSignUpScreen() {
    var view = new SignUpView(this, signUpPresenter, cmdTools);
    showView(view);
  }

  @Override
  public void openUserMenuScreen() {
    var view = new UserMenuView(this, userMenuPresenter, cmdTools);
    showView(view);
  }

  @Override
  public void openDeckSelectionScreen() {
    var view = new DeckSelectionView(this, deckSelectionPresenter, cmdTools);
    showView(view);
  }

  @Override
  public void openDeckCreationScreen() {
    var view = new DeckCreationView(this, mainPresenter, cmdTools);
    showView(view);
  }

  @Override
  public void openCollectionImportScreen() {
    var view = new CollectionImportView(this, collectionImportPresenter, cmdTools);
    showView(view);
  }

  @Override
  public void openDeckPlayerScreen() {
    var view = new DeckPlayerView(this, deckPlayerPresenter, cmdTools);
    showView(view);
  }

  private void showView(Core.View view) {
    if (currentView != null) {
      viewStack.push(currentView);
    }
    currentView = view;
    view.show();
  }
}
