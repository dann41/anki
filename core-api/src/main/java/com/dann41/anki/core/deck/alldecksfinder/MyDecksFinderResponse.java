package com.dann41.anki.core.deck.alldecksfinder;

import com.dann41.anki.shared.application.QueryResponse;

import java.util.List;

public record MyDecksFinderResponse(List<DeckSummary> decks) implements QueryResponse {
}
