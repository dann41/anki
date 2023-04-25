package com.dann41.anki.core.user.application.userregistrerer;

import com.dann41.anki.shared.application.CommandHandler;

public class RegisterUserCommandHandler implements CommandHandler<RegisterUserCommand> {
    private final UserRegisterer userRegisterer;

    public RegisterUserCommandHandler(UserRegisterer userRegisterer) {
        this.userRegisterer = userRegisterer;
    }

    @Override
    public void handle(RegisterUserCommand command) {
        userRegisterer.execute(command);
    }

    @Override
    public Class<RegisterUserCommand> supports() {
        return RegisterUserCommand.class;
    }
}
