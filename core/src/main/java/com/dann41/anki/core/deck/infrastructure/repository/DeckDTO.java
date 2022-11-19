package com.dann41.anki.core.deck.infrastructure.repository;

import com.dann41.anki.core.deck.domain.Deck;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public record DeckDTO(
    @JsonProperty("id") String id,
    @JsonProperty("userId") String userId,
    @JsonProperty("questions") List<QuestionDTO> questions,
    @JsonProperty("unansweredCards") List<String> unansweredCards,
    @JsonProperty("cardsInRedBox") List<String> cardsInRedBox,
    @JsonProperty("cardsInOrangeBox") List<String> cardsInOrangeBox,
    @JsonProperty("cardsInGreenBox") List<String> cardsInGreenBox,
    @JsonProperty("session") LocalDate session,
    @JsonProperty("isDeleted") boolean isDeleted
) {

  public Deck toDeck() {
    return Deck.restore(
        id(),
        userId(),
        questions().stream().map(QuestionDTO::toQuestion).collect(Collectors.toList()),
        unansweredCards(),
        cardsInRedBox(),
        cardsInOrangeBox(),
        cardsInGreenBox(),
        session(),
        isDeleted()
    );
  }

  public static DeckDTO fromDeck(Deck deck) {
    return new DeckDTO(
        deck.id().value(),
        deck.userId().value(),
        deck.questions().stream().map(QuestionDTO::fromQuestion).collect(Collectors.toList()),
        deck.unansweredCards(),
        deck.cardsInRedBox(),
        deck.cardsInOrangeBox(),
        deck.cardsInGreenBox(),
        deck.session(),
        deck.isDeleted()
    );
  }
}
