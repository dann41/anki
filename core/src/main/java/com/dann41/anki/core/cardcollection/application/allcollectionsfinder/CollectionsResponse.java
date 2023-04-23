package com.dann41.anki.core.cardcollection.application.allcollectionsfinder;

import com.dann41.anki.shared.application.QueryResponse;

import java.util.List;

public record CollectionsResponse(List<CardCollectionSummary> collections) implements QueryResponse {

}

