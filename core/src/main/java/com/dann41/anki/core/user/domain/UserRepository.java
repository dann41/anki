package com.dann41.anki.core.user.domain;

public interface UserRepository {

  User findById(UserId userId);

}
