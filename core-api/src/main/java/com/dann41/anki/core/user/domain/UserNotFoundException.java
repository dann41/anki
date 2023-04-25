package com.dann41.anki.core.user.domain;

public class UserNotFoundException extends RuntimeException {
    private final UserId userId;

    public UserNotFoundException(UserId userId) {
        super("User not found with id " + userId);
        this.userId = userId;
    }

    public UserNotFoundException(Username username) {
        super("User not found with username " + username);
        this.userId = null;
    }

    public UserId userId() {
        return userId;
    }
}
