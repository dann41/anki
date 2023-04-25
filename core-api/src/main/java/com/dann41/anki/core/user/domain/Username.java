package com.dann41.anki.core.user.domain;

public record Username(String value) {

    public Username {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("UserName cannot be empty");
        }
    }

}
