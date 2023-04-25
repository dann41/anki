package com.dann41.anki.core.cardcollection.domain;

public class CollectionNotFoundException extends RuntimeException {
    public CollectionNotFoundException(CardCollectionId id) {
        super("Collection not found for id " + id.value());
    }
}
