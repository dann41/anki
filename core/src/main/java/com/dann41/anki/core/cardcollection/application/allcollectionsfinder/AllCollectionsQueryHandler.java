package com.dann41.anki.core.cardcollection.application.allcollectionsfinder;

import com.dann41.anki.shared.application.QueryHandler;

public class AllCollectionsQueryHandler implements QueryHandler<AllCollectionsQuery, CollectionsResponse> {

    private final AllCollectionsFinder allCollectionsFinder;

    public AllCollectionsQueryHandler(AllCollectionsFinder allCollectionsFinder) {
        this.allCollectionsFinder = allCollectionsFinder;
    }

    @Override
    public Class<AllCollectionsQuery> supports() {
        return AllCollectionsQuery.class;
    }

    @Override
    public CollectionsResponse handle(AllCollectionsQuery query) {
        return allCollectionsFinder.execute();
    }
}
