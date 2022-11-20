package com.dann41.anki.core.user.application.authenticator;

public record UserAuthenticatorCommand(String userId, CharSequence password) {
}
