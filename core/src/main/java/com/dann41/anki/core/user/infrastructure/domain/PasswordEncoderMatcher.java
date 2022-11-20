package com.dann41.anki.core.user.infrastructure.domain;

import com.dann41.anki.core.user.domain.PasswordMatcher;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderMatcher implements PasswordMatcher {

  private final PasswordEncoder passwordEncoder;

  public PasswordEncoderMatcher(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public boolean matches(CharSequence rawPassword, String passwordHash) {
    return passwordEncoder.matches(rawPassword, passwordHash);
  }
}
