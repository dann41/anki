package com.dann41.anki.api.infrastructure.auth;


import org.springframework.security.core.AuthenticationException;

public class InvalidJwtException extends AuthenticationException {
    public InvalidJwtException(String jwt, Throwable cause) {
        super("Invalid token " + jwt, cause);
    }
}
