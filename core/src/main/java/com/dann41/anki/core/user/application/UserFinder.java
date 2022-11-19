package com.dann41.anki.core.user.application;

import com.dann41.anki.core.user.domain.User;
import com.dann41.anki.core.user.domain.UserId;
import com.dann41.anki.core.user.domain.UserNotFoundException;
import com.dann41.anki.core.user.domain.UserRepository;

public class UserFinder {

  private final UserRepository userRepository;

  public UserFinder(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public UserResponse execute(String userId) {
    UserId id = new UserId(userId);
    User user = userRepository.findById(id);
    if (user == null) {
      throw new UserNotFoundException(id);
    }

    return new UserResponse(user.userId().value());
  }
}
