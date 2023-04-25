package com.dann41.anki.core.deck.application.statefinder;

import com.dann41.anki.core.deck.statefinder.FindStatusQuery;
import com.dann41.anki.core.deck.statefinder.FindStatusResponse;
import com.dann41.anki.shared.application.QueryHandler;

public class FindStatusQueryHandler implements QueryHandler<FindStatusQuery, FindStatusResponse> {
    private final StatusFinder statusFinder;

    public FindStatusQueryHandler(StatusFinder statusFinder) {
        this.statusFinder = statusFinder;
    }

    @Override
    public FindStatusResponse handle(FindStatusQuery query) {
        return statusFinder.execute(query);
    }

    @Override
    public Class<FindStatusQuery> supports() {
        return FindStatusQuery.class;
    }
}
