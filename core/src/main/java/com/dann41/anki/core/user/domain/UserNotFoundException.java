package com.dann41.anki.core.user.domain;

public class UserNotFoundException extends RuntimeException {
  private final UserId userId;

  public UserNotFoundException(UserId userId) {
    super("User not found with id " + userId);
    this.userId = userId;
  }

  public UserId userId() {
    return userId;
  }
}
