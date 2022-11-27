package com.dann41.anki.core.user.domain;

public interface UserRepository {
  User findById(UserId userId);

  User findByUsername(Username username);

  void save(User user);
}
