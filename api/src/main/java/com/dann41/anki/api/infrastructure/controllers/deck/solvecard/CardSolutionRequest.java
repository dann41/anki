package com.dann41.anki.api.infrastructure.controllers.deck.solvecard;

public record CardSolutionRequest(
    String cardId,
    String box
) {
}
