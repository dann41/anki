package com.dann41.anki.core.user.application.userfinder;

import com.dann41.anki.shared.application.QueryHandler;

public class FindUserByIdQueryHandler implements QueryHandler<FindUserByIdQuery, FindUserResponse> {
    private final UserByIdFinder userByIdFinder;

    public FindUserByIdQueryHandler(UserByIdFinder userByIdFinder) {
        this.userByIdFinder = userByIdFinder;
    }

    @Override
    public FindUserResponse handle(FindUserByIdQuery query) {
        return userByIdFinder.execute(query);
    }

    @Override
    public Class<FindUserByIdQuery> supports() {
        return FindUserByIdQuery.class;
    }
}
