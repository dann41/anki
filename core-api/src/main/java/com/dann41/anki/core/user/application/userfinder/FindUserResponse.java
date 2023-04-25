package com.dann41.anki.core.user.application.userfinder;

import com.dann41.anki.shared.application.QueryResponse;

public record FindUserResponse(
        String id,
        String username,
        String passwordHash
) implements QueryResponse {
}
