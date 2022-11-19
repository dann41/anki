package com.dann41.anki.core.user.infrastructure.repository;

import com.dann41.anki.core.user.domain.User;
import com.dann41.anki.core.user.domain.UserId;
import com.dann41.anki.core.user.domain.UserRepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryUserRepository implements UserRepository {

  private final Map<UserId, User> users = new ConcurrentHashMap<>();

  public InMemoryUserRepository() {
    UserId userId = new UserId("1234");
    users.put(userId, new User(userId));
  }

  @Override
  public User findById(UserId userId) {
    return users.get(userId);
  }
}
