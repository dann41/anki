package com.dann41.anki.core.cardcollection.domain;

import com.dann41.anki.core.cardcollection.domain.card.Card;
import com.dann41.anki.core.cardcollection.domain.card.CardDTO;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Cards {

  private final Collection<Card> value;

  public Cards(Collection<CardDTO> value) {
    if (value == null || empty(value)) {
      throw new IllegalArgumentException("Cards expecting a non empty list of cards");
    }

    this.value = value.stream()
        .map(dto -> new Card(dto.question(), dto.answer()))
        .collect(Collectors.toList());
  }

  private static boolean empty(Collection<CardDTO> value) {
    return value.stream().allMatch(Objects::isNull);
  }

  public List<CardDTO> value() {
    return value.stream()
        .map(card -> new CardDTO(card.question(), card.answer()))
        .collect(Collectors.toList());
  }
}
