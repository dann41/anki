package com.dann41.anki.auth.infrastructure.auth;

import org.springframework.security.core.AuthenticationException;

public class TokenExpiredException extends AuthenticationException {

  public TokenExpiredException() {
    super("Token has expired");
  }

}
