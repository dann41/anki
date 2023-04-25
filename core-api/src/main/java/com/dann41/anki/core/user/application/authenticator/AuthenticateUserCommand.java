package com.dann41.anki.core.user.application.authenticator;

import com.dann41.anki.shared.application.Command;

public record AuthenticateUserCommand(String username, CharSequence password) implements Command {
}
