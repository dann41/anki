package com.dann41.anki.core.user.application.authenticator;

import com.dann41.anki.core.user.domain.PasswordMatcher;
import com.dann41.anki.core.user.domain.User;
import com.dann41.anki.core.user.domain.UserNotFoundException;
import com.dann41.anki.core.user.domain.UserRepository;
import com.dann41.anki.core.user.domain.Username;

public class UserAuthenticator {
  private final UserRepository userRepository;
  private final PasswordMatcher passwordMatcher;

  public UserAuthenticator(UserRepository userRepository, PasswordMatcher passwordMatcher) {
    this.userRepository = userRepository;
    this.passwordMatcher = passwordMatcher;
  }

  public void execute(UserAuthenticatorCommand command) {
    Username username = new Username(command.username());
    User user = userRepository.findByUsername(new Username(command.username()));
    if (user == null) {
      throw new UserNotFoundException(username);
    }

    if (!user.matchesPassword(command.password(), passwordMatcher)) {
      throw new UserNotFoundException(username);
    }
  }

}
