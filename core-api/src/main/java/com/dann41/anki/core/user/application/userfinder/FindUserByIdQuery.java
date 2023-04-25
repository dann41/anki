package com.dann41.anki.core.user.application.userfinder;

import com.dann41.anki.shared.application.Query;

public record FindUserByIdQuery(String userId) implements Query<FindUserResponse> {
}
