package com.dann41.anki.core.user.infrastructure.framework;

import com.dann41.anki.core.user.domain.UserRepository;
import com.dann41.anki.core.user.infrastructure.repository.InMemoryUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserRepositoryConfiguration {

  @Bean
  UserRepository userRepository(PasswordEncoder passwordEncoder) {
    return new InMemoryUserRepository(passwordEncoder);
  }

}
