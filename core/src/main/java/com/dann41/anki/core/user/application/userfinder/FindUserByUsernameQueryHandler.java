package com.dann41.anki.core.user.application.userfinder;

import com.dann41.anki.shared.application.QueryHandler;

public class FindUserByUsernameQueryHandler implements QueryHandler<FindUserByUsernameQuery, FindUserResponse> {
    private final UserByUsernameFinder userByUsernameFinder;

    public FindUserByUsernameQueryHandler(UserByUsernameFinder userByUsernameFinder) {
        this.userByUsernameFinder = userByUsernameFinder;
    }

    @Override
    public FindUserResponse handle(FindUserByUsernameQuery query) {
        return userByUsernameFinder.execute(query);
    }

    @Override
    public Class<FindUserByUsernameQuery> supports() {
        return FindUserByUsernameQuery.class;
    }
}
