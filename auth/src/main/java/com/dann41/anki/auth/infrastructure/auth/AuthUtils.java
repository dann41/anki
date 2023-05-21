package com.dann41.anki.auth.infrastructure.auth;

import org.springframework.security.core.Authentication;

public final class AuthUtils {

  public static String userIdFrom(Authentication authentication) {
    return ((AuthUser) authentication.getPrincipal()).getId();
  }

}
