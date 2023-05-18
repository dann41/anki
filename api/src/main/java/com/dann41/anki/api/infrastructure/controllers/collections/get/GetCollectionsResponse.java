package com.dann41.anki.api.infrastructure.controllers.collections.get;

import java.util.List;

public record GetCollectionsResponse(
        List<CollectionDto> items
) {
}
