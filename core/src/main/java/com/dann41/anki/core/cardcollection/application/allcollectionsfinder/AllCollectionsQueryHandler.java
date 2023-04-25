package com.dann41.anki.core.cardcollection.application.allcollectionsfinder;

import com.dann41.anki.shared.application.QueryHandler;

public class AllCollectionsQueryHandler implements QueryHandler<AllCollectionsQuery, AllCollectionsResponse> {

    private final AllCollectionsFinder allCollectionsFinder;

    public AllCollectionsQueryHandler(AllCollectionsFinder allCollectionsFinder) {
        this.allCollectionsFinder = allCollectionsFinder;
    }

    @Override
    public Class<AllCollectionsQuery> supports() {
        return AllCollectionsQuery.class;
    }

    @Override
    public AllCollectionsResponse handle(AllCollectionsQuery query) {
        return allCollectionsFinder.execute();
    }
}
