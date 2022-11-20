package com.dann41.anki.core.user.domain;

public class UserMother {

  public static final String USER_ID = "1234";

  public static User defaultUser() {
    return new User(
        new UserId("1234"),
        new PasswordHash("$2a$12$F.4Z8w9nmMoGhGu7aVVI1OwEPKw3u3cmHjeAJ7ZFUx147MGxM9b0m") // abcd
    );
  }

}
