package com.dann41.anki.api.infrastructure.controllers.deck.getcard;

public record CardResponse(
    String id,
    String question,
    String answer
) {
}
