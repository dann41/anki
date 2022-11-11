package com.dann41.anki.domain;

import com.dann41.anki.domain.deck.Deck;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

public class DeckMother {

  public static final String DECK_ID = "1234";

  public static Deck defaultDeck() {
    return Deck.restore(
        DECK_ID,
        Collections.singletonList("A"),
        Arrays.asList("B", "C"),
        Arrays.asList("D", "E", "F"),
        Arrays.asList("G", "H", "I", "J"),
        LocalDate.of(2022, 11, 10)
    );
  }

  public static Deck withoutUnplayed() {
    return Deck.restore(
        DECK_ID,
        Collections.emptyList(),
        Arrays.asList("A", "B", "C"),
        Arrays.asList("D", "E", "F"),
        Arrays.asList("G", "H", "I", "J"),
        LocalDate.of(2022, 11, 10)
    );
  }

  public static Deck withSession(LocalDate session) {
    return Deck.restore(
        DECK_ID,
        Collections.singletonList("A"),
        Collections.singletonList("B"),
        Collections.singletonList("C"),
        Collections.singletonList("D"),
        session
    );
  }

}
