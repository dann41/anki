package com.dann41.anki.core.user.infrastructure.framework;

import com.dann41.anki.core.user.application.authenticator.UserAuthenticator;
import com.dann41.anki.core.user.application.userfinder.UserFinder;
import com.dann41.anki.core.user.domain.PasswordMatcher;
import com.dann41.anki.core.user.domain.UserRepository;
import com.dann41.anki.core.user.infrastructure.domain.PasswordEncoderMatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserConfiguration {

  @Bean
  UserFinder userFinder(UserRepository userRepository) {
    return new UserFinder(userRepository);
  }

  @Bean
  UserAuthenticator userAuthenticator(UserRepository userRepository, PasswordMatcher passwordMatcher) {
    return new UserAuthenticator(userRepository, passwordMatcher);
  }

  @Bean
  PasswordMatcher passwordMatcher(PasswordEncoder passwordEncoder) {
    return new PasswordEncoderMatcher(passwordEncoder);
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(512);
  }

}
