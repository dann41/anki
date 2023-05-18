package com.dann41.anki.api.infrastructure.controllers.collections.get;

public record CollectionDto(
        String id,
        String name,
        String description,
        int numberOfQuestions) {
}
