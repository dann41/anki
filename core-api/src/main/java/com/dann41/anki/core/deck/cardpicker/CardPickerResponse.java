package com.dann41.anki.core.deck.cardpicker;

import com.dann41.anki.shared.application.QueryResponse;

public record CardPickerResponse(String id, String question, String answer) implements QueryResponse {

}
