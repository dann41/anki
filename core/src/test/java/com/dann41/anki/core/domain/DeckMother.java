package com.dann41.anki.core.domain;

import com.dann41.anki.core.domain.deck.Deck;
import com.dann41.anki.core.domain.deck.Question;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DeckMother {

  public static final String DECK_ID = "1234";

  public static Deck defaultDeck() {
    return Deck.restore(
        DECK_ID,
        questions(),
        Collections.singletonList("A"),
        Arrays.asList("B", "C"),
        Arrays.asList("D", "E", "F"),
        Arrays.asList("G", "H", "I", "J"),
        LocalDate.of(2022, 11, 10)
    );
  }

  public static Deck withoutUnanswered() {
    return Deck.restore(
        DECK_ID,
        questions(),
        Collections.emptyList(),
        Arrays.asList("A", "B", "C"),
        Arrays.asList("D", "E", "F"),
        Arrays.asList("G", "H", "I", "J"),
        LocalDate.of(2022, 11, 10)
    );
  }

  public static Deck withoutPendingForToday() {
    return Deck.restore(
        DECK_ID,
        questions(),
        Collections.emptyList(),
        Collections.emptyList(),
        Arrays.asList("A", "B", "C", "D", "E", "F"),
        Arrays.asList("G", "H", "I", "J"),
        LocalDate.of(2022, 11, 10)
    );
  }

  public static Deck withSession(LocalDate session) {
    return Deck.restore(
        DECK_ID,
        questions(),
        Collections.singletonList("A"),
        Collections.singletonList("B"),
        Collections.singletonList("C"),
        Collections.singletonList("D"),
        session
    );
  }

  private static List<Question> questions() {
    return Arrays.asList(
        new Question("A", "Answer A"),
        new Question("B", "Answer B"),
        new Question("C", "Answer C"),
        new Question("D", "Answer D"),
        new Question("E", "Answer E"),
        new Question("F", "Answer F"),
        new Question("G", "Answer G"),
        new Question("H", "Answer H"),
        new Question("I", "Answer I"),
        new Question("J", "Answer J")
    );
  }

}
