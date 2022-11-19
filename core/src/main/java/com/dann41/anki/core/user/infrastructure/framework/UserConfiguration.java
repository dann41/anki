package com.dann41.anki.core.user.infrastructure.framework;

import com.dann41.anki.core.user.application.UserFinder;
import com.dann41.anki.core.user.domain.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfiguration {

  @Bean
  UserFinder userFinder(UserRepository userRepository) {
    return new UserFinder(userRepository);
  }

}
