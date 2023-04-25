package com.dann41.anki.core.user.infrastructure.framework;

import com.dann41.anki.core.user.application.authenticator.AuthenticateUserCommandHandler;
import com.dann41.anki.core.user.application.authenticator.UserAuthenticator;
import com.dann41.anki.core.user.application.userfinder.FindUserByIdQueryHandler;
import com.dann41.anki.core.user.application.userfinder.FindUserByUsernameQueryHandler;
import com.dann41.anki.core.user.application.userfinder.UserByIdFinder;
import com.dann41.anki.core.user.application.userfinder.UserByUsernameFinder;
import com.dann41.anki.core.user.application.userregistrerer.RegisterUserCommandHandler;
import com.dann41.anki.core.user.application.userregistrerer.UserRegisterer;
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
    UserByUsernameFinder userByUsernameFinder(UserRepository userRepository) {
        return new UserByUsernameFinder(userRepository);
    }

    @Bean
    UserByIdFinder userByIdFinder(UserRepository userRepository) {
        return new UserByIdFinder(userRepository);
    }

    @Bean
    UserRegisterer userRegistrerer(UserRepository userRepository) {
        return new UserRegisterer(userRepository);
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
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    AuthenticateUserCommandHandler authenticateUserCommandHandler(UserAuthenticator userAuthenticator) {
        return new AuthenticateUserCommandHandler(userAuthenticator);
    }

    @Bean
    FindUserByUsernameQueryHandler findUserByUsernameQueryHandler(UserByUsernameFinder userByUsernameFinder) {
        return new FindUserByUsernameQueryHandler(userByUsernameFinder);
    }

    @Bean
    FindUserByIdQueryHandler findUserByIdQueryHandler(UserByIdFinder userByIdFinder) {
        return new FindUserByIdQueryHandler(userByIdFinder);
    }

    @Bean
    RegisterUserCommandHandler registerUserCommandHandler(UserRegisterer userRegisterer) {
        return new RegisterUserCommandHandler(userRegisterer);
    }
}
