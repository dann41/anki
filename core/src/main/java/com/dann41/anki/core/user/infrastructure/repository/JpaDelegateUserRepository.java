package com.dann41.anki.core.user.infrastructure.repository;

import com.dann41.anki.core.user.domain.User;
import com.dann41.anki.core.user.domain.UserId;
import com.dann41.anki.core.user.domain.UserRepository;
import com.dann41.anki.core.user.domain.Username;
import com.dann41.anki.core.user.infrastructure.repository.jpa.UserDTO;
import com.dann41.anki.core.user.infrastructure.repository.jpa.UserEntity;
import com.dann41.anki.core.user.infrastructure.repository.jpa.UserJpaRepository;

public class JpaDelegateUserRepository implements UserRepository {

  private final UserJpaRepository jpaRepository;

  public JpaDelegateUserRepository(UserJpaRepository jpaRepository) {
    this.jpaRepository = jpaRepository;
  }

  @Override
  public User findById(UserId userId) {
    return jpaRepository.findById(userId.value())
        .map(UserEntity::toDomain)
        .orElse(null);
  }

  @Override
  public User findByUsername(Username username) {
    return jpaRepository.findByUsername(username.value())
        .map(UserEntity::toDomain)
        .orElse(null);
  }

  @Override
  public void save(User user) {
    jpaRepository.saveAndFlush(
        UserEntity.of(
            user.userId(),
            user.userName(),
            UserDTO.fromDomain(user),
            0L
        ));
  }
}
