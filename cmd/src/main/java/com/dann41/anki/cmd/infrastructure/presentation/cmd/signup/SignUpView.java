package com.dann41.anki.cmd.infrastructure.presentation.cmd.signup;

import com.dann41.anki.cmd.infrastructure.presentation.cmd.CmdTools;
import com.dann41.anki.cmd.infrastructure.presentation.cmd.RetriesExhaustedException;
import com.dann41.anki.cmd.infrastructure.presentation.cmd.Retryer;
import com.dann41.anki.cmd.infrastructure.presentation.cmd.core.BaseView;
import com.dann41.anki.cmd.infrastructure.presentation.cmd.core.Navigator;

public class SignUpView extends BaseView implements SignUpContract.View {
  private static final int MAX_TRIES = 3;
  private final SignUpContract.Presenter presenter;
  private final CmdTools cmdTools;
  private final Retryer retryer;

  public SignUpView(Navigator navigator, SignUpContract.Presenter presenter, CmdTools cmdTools) {
    super(navigator);
    this.presenter = presenter;
    this.cmdTools = cmdTools;
    this.retryer = new Retryer(MAX_TRIES);
  }

  @Override
  public void show() {
    configurePresenter(presenter);

    System.out.println("--- REGISTER ---");
    displaySignUp();
  }

  private void displaySignUp() {
    try {
      retryer.retry(() -> {
        System.out.print("Username: ");
        String username = cmdTools.readLine();
        System.out.print("Password: ");
        String password = cmdTools.readLine();

        presenter.onSignupSubmit(username, password);
      });
    } catch (RetriesExhaustedException e) {
      cmdTools.printError("Too may tries... back to auth menu");
      goBack();
    }
  }

  @Override
  public void showSignupSucceed() {
    cmdTools.printInfo("User created");
  }

  @Override
  public void showSignupFailed() {
    cmdTools.printError("Cannot create user");
    displaySignUp();
  }
}
