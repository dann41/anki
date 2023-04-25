package com.dann41.anki.core.deck.application.statefinder;

import com.dann41.anki.shared.application.Query;

public record FindStatusQuery(String deckId, String userId) implements Query<FindStatusResponse> {
}