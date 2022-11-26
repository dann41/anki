package com.dann41.anki.core.cardcollection.application.collectionsimporter;

import java.util.List;

public record ImportCollectionsCommand(List<CardCollectionRequest> collections) {
}
