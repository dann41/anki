package com.dann41.anki.core.user.application.userregistrerer;

import com.dann41.anki.core.user.domain.PasswordHash;
import com.dann41.anki.core.user.domain.User;
import com.dann41.anki.core.user.domain.UserId;
import com.dann41.anki.core.user.domain.UserRepository;
import com.dann41.anki.core.user.domain.Username;

import java.util.UUID;

public class UserRegisterer {

  private final UserRepository userRepository;

  public UserRegisterer(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public void execute(RegisterUserCommand command) {
    User existingUser = userRepository.findByUsername(new Username(command.username()));
    if (existingUser != null) {
      throw new UserAlreadyExistException("Cannot register user with username " + command.username());
    }

    User newUser = new User(
        new UserId(UUID.randomUUID().toString()),
        new Username(command.username()),
        new PasswordHash(command.passwordHash())
    );
    userRepository.save(newUser);
  }
}
