package com.dann41.anki.core.user.infrastructure.framework;

import com.dann41.anki.core.user.domain.UserRepository;
import com.dann41.anki.core.user.infrastructure.repository.JpaDelegateUserRepository;
import com.dann41.anki.core.user.infrastructure.repository.jpa.UserJpaRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.dann41.anki.core.user.infrastructure.repository.jpa")
@ComponentScan(basePackages = { "com.dann41.anki.core.user.infrastructure.repository.*" })
@EntityScan("com.dann41.anki.core.user.infrastructure.repository.*")
@Configuration
public class UserRepositoryConfiguration {
  @Bean
  UserRepository userRepository(UserJpaRepository userJpaRepository) {
    return new JpaDelegateUserRepository(userJpaRepository);
  }
}
