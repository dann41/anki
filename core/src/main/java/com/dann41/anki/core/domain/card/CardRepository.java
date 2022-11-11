package com.dann41.anki.core.domain.card;

import java.util.Collection;

public interface CardRepository {

  Card findCardById(String cardId);

  Collection<Card> getAll();

}
