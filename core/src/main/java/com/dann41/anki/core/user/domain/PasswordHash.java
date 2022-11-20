package com.dann41.anki.core.user.domain;

public record PasswordHash(String value) {

  public PasswordHash {
    if (value == null || value.isEmpty() || value.isBlank()) {
      throw new IllegalArgumentException("PasswordHash cannot be empty");
    }
  }

}
