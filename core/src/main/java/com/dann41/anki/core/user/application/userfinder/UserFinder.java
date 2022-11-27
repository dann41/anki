package com.dann41.anki.core.user.application.userfinder;

import com.dann41.anki.core.user.domain.User;
import com.dann41.anki.core.user.domain.UserNotFoundException;
import com.dann41.anki.core.user.domain.UserRepository;
import com.dann41.anki.core.user.domain.Username;

public class UserFinder {

  private final UserRepository userRepository;

  public UserFinder(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public UserResponse execute(String username) {
    Username usernameVO = new Username(username);
    User user = userRepository.findByUsername(usernameVO);
    if (user == null) {
      throw new UserNotFoundException(usernameVO);
    }

    return new UserResponse(user.userId(), user.userName());
  }
}
