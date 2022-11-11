package com.dann41.anki.core.infrastructure.repository.deck;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.List;

public class DeckDTO {
  @JsonProperty("id")
  private final String id;
  @JsonProperty("unplayedCards")
  private final List<String> unplayedCards;
  @JsonProperty("cardsInRedBox")
  private final List<String> cardsInRedBox;
  @JsonProperty("cardsInOrangeBox")
  private final List<String> cardsInOrangeBox;
  @JsonProperty("cardsInGreenBox")
  private final List<String> cardsInGreenBox;
  @JsonProperty("session")
  private final LocalDate session;

  @JsonCreator
  public DeckDTO(
      @JsonProperty("id") String id,
      @JsonProperty("unplayedCards") List<String> unplayedCards,
      @JsonProperty("cardsInRedBox") List<String> cardsInRedBox,
      @JsonProperty("cardsInOrangeBox") List<String> cardsInOrangeBox,
      @JsonProperty("cardsInGreenBox") List<String> cardsInGreenBox,
      @JsonProperty("session") LocalDate session
  ) {
    this.id = id;
    this.unplayedCards = unplayedCards;
    this.cardsInRedBox = cardsInRedBox;
    this.cardsInOrangeBox = cardsInOrangeBox;
    this.cardsInGreenBox = cardsInGreenBox;
    this.session = session;
  }

  public String id() {
    return id;
  }

  public List<String> unplayedCards() {
    return unplayedCards;
  }

  public List<String> cardsInRedBox() {
    return cardsInRedBox;
  }

  public List<String> cardsInOrangeBox() {
    return cardsInOrangeBox;
  }

  public List<String> cardsInGreenBox() {
    return cardsInGreenBox;
  }

  public LocalDate session() {
    return session;
  }
}
