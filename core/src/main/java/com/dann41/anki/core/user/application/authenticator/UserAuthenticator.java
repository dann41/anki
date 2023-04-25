package com.dann41.anki.core.user.application.authenticator;

import com.dann41.anki.core.user.domain.*;

public class UserAuthenticator {
    private final UserRepository userRepository;
    private final PasswordMatcher passwordMatcher;

    public UserAuthenticator(UserRepository userRepository, PasswordMatcher passwordMatcher) {
        this.userRepository = userRepository;
        this.passwordMatcher = passwordMatcher;
    }

    public void execute(AuthenticateUserCommand command) {
        Username username = new Username(command.username());
        User user = userRepository.findByUsername(new Username(command.username()));
        if (user == null) {
            throw new UserNotFoundException(username);
        }

        if (!user.matchesPassword(command.password(), passwordMatcher)) {
            throw new UserNotFoundException(username);
        }
    }

}
