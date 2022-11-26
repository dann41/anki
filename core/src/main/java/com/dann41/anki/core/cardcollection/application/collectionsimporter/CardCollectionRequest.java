package com.dann41.anki.core.cardcollection.application.collectionsimporter;

import java.util.Collection;

public record CardCollectionRequest(String id, String name, Collection<CardRequest> cards) {
}
