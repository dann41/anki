package com.dann41.anki.core.user.application.userfinder;

import com.dann41.anki.shared.application.Query;

public record FindUserByUsernameQuery(String username) implements Query<FindUserResponse> {
}
