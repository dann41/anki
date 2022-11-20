package com.dann41.anki.core.user.domain;

import java.time.LocalDate;

public class User {

  private final UserId userId;
  private final PasswordHash passwordHash;

  public User(UserId userId, PasswordHash passwordHash) {
    if (userId == null) {
      throw new IllegalArgumentException("UserId cannot be null");
    }

    if (passwordHash == null) {
      throw new IllegalArgumentException("Password cannot be null");
    }

    this.userId = userId;
    this.passwordHash = passwordHash;
  }

  public UserId userId() {
    return userId;
  }

  public boolean matchesPassword(CharSequence password, PasswordMatcher passwordMatcher) {
    // TODO add max number of retries
    return passwordMatcher.matches(password, passwordHash.value());
  }
}
