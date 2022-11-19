package com.dann41.anki.core.user.domain;

public class User {

  private final UserId userId;

  public User(UserId userId) {
    if (userId == null) {
      throw new IllegalArgumentException("UserId cannot be null");
    }
    this.userId = userId;
  }

  public UserId userId() {
    return userId;
  }
}
