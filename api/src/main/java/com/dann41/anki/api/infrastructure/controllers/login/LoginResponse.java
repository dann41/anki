package com.dann41.anki.api.infrastructure.controllers.login;

public record LoginResponse(
    String userId,
    String userName,
    String accessToken
) {
}
