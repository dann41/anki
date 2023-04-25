package com.dann41.anki.core.user.application.authenticator;

import com.dann41.anki.shared.application.CommandHandler;

public class AuthenticateUserCommandHandler implements CommandHandler<AuthenticateUserCommand> {
    private final UserAuthenticator userAuthenticator;

    public AuthenticateUserCommandHandler(UserAuthenticator userAuthenticator) {
        this.userAuthenticator = userAuthenticator;
    }

    @Override
    public void handle(AuthenticateUserCommand command) {
        userAuthenticator.execute(command);
    }

    @Override
    public Class<AuthenticateUserCommand> supports() {
        return AuthenticateUserCommand.class;
    }
}
