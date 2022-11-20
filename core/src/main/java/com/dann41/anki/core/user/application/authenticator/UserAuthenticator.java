package com.dann41.anki.core.user.application.authenticator;

import com.dann41.anki.core.user.domain.PasswordMatcher;
import com.dann41.anki.core.user.domain.User;
import com.dann41.anki.core.user.domain.UserId;
import com.dann41.anki.core.user.domain.UserNotFoundException;
import com.dann41.anki.core.user.domain.UserRepository;

public class UserAuthenticator {
  private final UserRepository userRepository;
  private final PasswordMatcher passwordMatcher;

  public UserAuthenticator(UserRepository userRepository, PasswordMatcher passwordMatcher) {
    this.userRepository = userRepository;
    this.passwordMatcher = passwordMatcher;
  }

  public void execute(UserAuthenticatorCommand command) {
    UserId id = new UserId(command.userId());
    User user = userRepository.findById(new UserId(command.userId()));
    if (user == null) {
      throw new UserNotFoundException(id);
    }

    if (!user.matchesPassword(command.password(), passwordMatcher)) {
      throw new UserNotFoundException(id);
    }
  }

}
