package com.dann41.anki.cmd.intrastructure.presentation.cmd.model.session;

import com.dann41.anki.core.user.application.authenticator.UserAuthenticator;
import com.dann41.anki.core.user.application.authenticator.UserAuthenticatorCommand;
import com.dann41.anki.core.user.application.userfinder.UserFinder;
import com.dann41.anki.core.user.application.userfinder.UserResponse;
import org.springframework.stereotype.Service;

@Service
public class SessionInteractor {

  private final UserAuthenticator userAuthenticator;
  private final UserFinder userFinder;

  private Session session;
  private String deckId;

  public SessionInteractor(UserAuthenticator userAuthenticator, UserFinder userFinder) {
    this.userAuthenticator = userAuthenticator;
    this.userFinder = userFinder;
  }

  public void login(String username, String password) {
    userAuthenticator.execute(new UserAuthenticatorCommand(username, password));
    UserResponse user = userFinder.execute(username);
    this.session = new Session(
        user.id(),
        user.username()
    );
  }

  public Session currentSession() {
    return session;
  }

  public void logout() {
    session = null;
  }

  public void selectDeck(String deckId) {
    this.deckId = deckId;
  }

  public void endDeck() {
    this.deckId = null;
  }

  public String deckSelected() {
    return deckId;
  }
}
