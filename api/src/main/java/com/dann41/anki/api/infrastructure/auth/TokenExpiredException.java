package com.dann41.anki.api.infrastructure.auth;

public class TokenExpiredException extends RuntimeException {

  public TokenExpiredException() {
    super("Token has expired");
  }

}
