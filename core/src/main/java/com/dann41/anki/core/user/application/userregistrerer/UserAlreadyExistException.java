package com.dann41.anki.core.user.application.userregistrerer;

public class UserAlreadyExistException extends RuntimeException {
  public UserAlreadyExistException(String message) {
    super(message);
  }
}
