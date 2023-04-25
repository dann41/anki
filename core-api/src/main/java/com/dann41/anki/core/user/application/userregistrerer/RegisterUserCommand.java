package com.dann41.anki.core.user.application.userregistrerer;

import com.dann41.anki.shared.application.Command;

public record RegisterUserCommand(String username, String passwordHash) implements Command {
}
