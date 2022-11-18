package com.dann41.anki.core.cardcollection.domain;

import com.dann41.anki.core.cardcollection.domain.card.CardDTO;
import com.dann41.anki.core.cardcollection.domain.CardCollection;

import java.util.List;

public class CardCollectionMother {

  public static CardCollection defaultCollection() {
    return new CardCollection(
      "1",
      "arts",
        "question regarding art",
        List.of(
            new CardDTO("A", "Answer A"),
            new CardDTO("B", "Answer B"),
            new CardDTO("C", "Answer C"),
            new CardDTO("D", "Answer D"),
            new CardDTO("E", "Answer E"),
            new CardDTO("F", "Answer F")
        )
    );
  }

}
