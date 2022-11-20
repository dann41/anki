package com.dann41.anki.core.user.domain;

public interface PasswordMatcher {

  boolean matches(CharSequence rawPassword, String passwordHash);

}
