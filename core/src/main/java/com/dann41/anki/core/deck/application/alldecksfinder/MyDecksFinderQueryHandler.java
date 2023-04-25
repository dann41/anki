package com.dann41.anki.core.deck.application.alldecksfinder;

import com.dann41.anki.core.deck.alldecksfinder.MyDecksFinderQuery;
import com.dann41.anki.core.deck.alldecksfinder.MyDecksFinderResponse;
import com.dann41.anki.shared.application.QueryHandler;

public class MyDecksFinderQueryHandler implements QueryHandler<MyDecksFinderQuery, MyDecksFinderResponse> {

    private final MyDecksFinder myDecksFinder;

    public MyDecksFinderQueryHandler(MyDecksFinder myDecksFinder) {
        this.myDecksFinder = myDecksFinder;
    }

    @Override
    public MyDecksFinderResponse handle(MyDecksFinderQuery query) {
        return myDecksFinder.execute(query);
    }

    @Override
    public Class<MyDecksFinderQuery> supports() {
        return MyDecksFinderQuery.class;
    }
}
