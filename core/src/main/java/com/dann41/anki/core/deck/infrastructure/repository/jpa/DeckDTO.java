package com.dann41.anki.core.deck.infrastructure.repository.jpa;

import com.dann41.anki.core.deck.domain.Deck;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public record DeckDTO(
    @JsonProperty("id") String id,
    @JsonProperty("userId") String userId,
    @JsonProperty("collectionId") String collectionId,
    @JsonProperty("questions") List<QuestionDTO> questions,
    @JsonProperty("unansweredCards") List<String> unansweredCards,
    @JsonProperty("cardsInRedBox") List<String> cardsInRedBox,
    @JsonProperty("cardsInOrangeBox") List<String> cardsInOrangeBox,
    @JsonProperty("cardsInGreenBox") List<String> cardsInGreenBox,
    @JsonProperty("session") LocalDate session,
    @JsonProperty("isDeleted") boolean isDeleted
) {

  public static DeckDTO from(Deck deck) {
    return new DeckDTO(
        deck.id().value(),
        deck.userId().value(),
        deck.collectionId(),
        deck.questions().stream().map(QuestionDTO::fromQuestion).collect(Collectors.toList()),
        deck.unansweredCards(),
        deck.cardsInRedBox(),
        deck.cardsInOrangeBox(),
        deck.cardsInGreenBox(),
        deck.session(),
        deck.isDeleted()
    );
  }

  public Deck toDomain() {
    return Deck.restore(
        this.id(),
        this.userId(),
        this.collectionId(),
        this.questions().stream().map(QuestionDTO::toQuestion).collect(Collectors.toList()),
        this.unansweredCards(),
        this.cardsInRedBox(),
        this.cardsInOrangeBox(),
        this.cardsInGreenBox(),
        this.session(),
        this.isDeleted()
    );
  }
}
