package com.dann41.anki.core.user.infrastructure.repository;

import com.dann41.anki.core.user.domain.PasswordHash;
import com.dann41.anki.core.user.domain.User;
import com.dann41.anki.core.user.domain.UserId;
import com.dann41.anki.core.user.domain.UserRepository;
import com.dann41.anki.core.user.domain.Username;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryUserRepository implements UserRepository {

  private final Map<UserId, User> users = new ConcurrentHashMap<>();

  public InMemoryUserRepository(PasswordEncoder passwordEncoder) {

    UserId userId = new UserId("1234");
    Username username = new Username("dann41");
    PasswordHash passwordHash = new PasswordHash(passwordEncoder.encode("abcd"));

    users.put(userId, new User(userId, username, passwordHash));
  }

  @Override
  public User findById(UserId userId) {
    return users.get(userId);
  }

  @Override
  public User findByUsername(Username username) {
    return users.values().stream()
        .filter(user -> Objects.equals(username, user.userName()))
        .findFirst()
        .orElse(null);
  }
}
