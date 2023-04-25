package com.dann41.anki.core.deck.cardpicker;

import com.dann41.anki.shared.application.Query;

public record CardPickerQuery(String deckId, String userId) implements Query<CardPickerResponse> {

}
