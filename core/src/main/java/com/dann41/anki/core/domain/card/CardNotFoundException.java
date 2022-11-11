package com.dann41.anki.core.domain.card;

public class CardNotFoundException extends RuntimeException {
  public CardNotFoundException(String cardId) {
    super("Card not found for id " + cardId);
  }
}
