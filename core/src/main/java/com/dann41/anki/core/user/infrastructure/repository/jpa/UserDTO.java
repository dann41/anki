package com.dann41.anki.core.user.infrastructure.repository.jpa;

import com.dann41.anki.core.user.domain.PasswordHash;
import com.dann41.anki.core.user.domain.User;
import com.dann41.anki.core.user.domain.UserId;
import com.dann41.anki.core.user.domain.Username;

public record UserDTO(String id, String username, String passwordHash) {
  public static UserDTO fromDomain(User user) {
    return new UserDTO(
      user.userId(),
      user.userName(),
      user.passwordHash()
    );
  }

  public User toDomain() {
    return new User(
        new UserId(id),
        new Username(username),
        new PasswordHash(passwordHash)
    );
  }
}
