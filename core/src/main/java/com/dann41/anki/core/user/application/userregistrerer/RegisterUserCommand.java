package com.dann41.anki.core.user.application.userregistrerer;

public record RegisterUserCommand(String username, String passwordHash) {
}
