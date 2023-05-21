package com.dann41.anki.api.infrastructure.controllers.collection.get;

import java.util.List;

public record GetCollectionsResponse(
        List<CollectionDto> items
) {
}
