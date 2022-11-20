package com.dann41.anki.core.user.domain;

public class User {

  private final UserId userId;
  private final Username userName;
  private final PasswordHash passwordHash;

  public User(UserId userId, Username userName, PasswordHash passwordHash) {
    if (userId == null) {
      throw new IllegalArgumentException("UserId cannot be null");
    }

    if (userName == null) {
      throw new IllegalArgumentException("UserName cannot be null");
    }

    if (passwordHash == null) {
      throw new IllegalArgumentException("Password cannot be null");
    }

    this.userId = userId;
    this.userName = userName;
    this.passwordHash = passwordHash;
  }

  public UserId userId() {
    return userId;
  }

  public Username userName() {
    return userName;
  }

  public boolean matchesPassword(CharSequence password, PasswordMatcher passwordMatcher) {
    // TODO add max number of retries
    return passwordMatcher.matches(password, passwordHash.value());
  }
}
