package com.dann41.anki.core.deck.application.alldecksfinder;

import com.dann41.anki.shared.application.Query;

public record MyDecksFinderQuery(String userId) implements Query<MyDecksFinderResponse> {
}
