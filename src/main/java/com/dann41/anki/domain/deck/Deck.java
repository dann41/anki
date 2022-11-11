package com.dann41.anki.domain.deck;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Deck {
  public static final String GREEN_BOX = "green";
  public static final String ORANGE_BOX = "orange";
  public static final String RED_BOX = "red";

  private final DeckId id;
  private final List<CardId> unplayedCards;
  private final Box redBox;
  private final Box orangeBox;
  private final Box greenBox;
  private Session session;

  public Deck(DeckId id, List<CardId> unplayedCards, Box redBox, Box orangeBox, Box greenBox, Session session) {
    this.id = id;
    this.unplayedCards = unplayedCards;
    this.redBox = redBox;
    this.orangeBox = orangeBox;
    this.greenBox = greenBox;
    this.session = session;
  }

  public static Deck create(String deckId, List<String> nonPlayedCards) {
    return new Deck(
        new DeckId(deckId),
        toCardIdList(nonPlayedCards),
        Box.create(Collections.emptyList()),
        Box.create(Collections.emptyList()),
        Box.create(Collections.emptyList()),
        null
    );
  }

  public static Deck restore(
      String deckId,
      List<String> nonPlayedCards,
      List<String> cardsInRedBox,
      List<String> cardsInOrangeBox,
      List<String> cardsInGreenBox,
      LocalDate lastSession
  ) {
    return new Deck(
        new DeckId(deckId),
        toCardIdList(nonPlayedCards),
        Box.create(cardsInRedBox),
        Box.create(cardsInOrangeBox),
        Box.create(cardsInGreenBox),
        Session.fromDate(lastSession)
    );
  }

  public DeckId id() {
    return id;
  }

  public List<String> unplayedCards() {
    return unplayedCards.stream().map(CardId::value).collect(Collectors.toList());
  }

  public List<String> cardsInRedBox() {
    return redBox.pullAllCards().stream().map(CardId::value).collect(Collectors.toList());
  }

  public List<String> cardsInOrangeBox() {
    return orangeBox.pullAllCards().stream().map(CardId::value).collect(Collectors.toList());
  }

  public List<String> cardsInGreenBox() {
    return greenBox.pullAllCards().stream().map(CardId::value).collect(Collectors.toList());
  }

  public LocalDate session() {
    return Optional.ofNullable(session).map(Session::value).orElse(null);
  }

  public void startNewSession(LocalDate today) {
    if (isFirstSession()) {
      session = Session.fromDate(today);
      // first session started
      return;
    }

    if (isTodaySession(today)) {
      // same session
      return;
    }

    // new session
    rotateBoxes(session.daysSinceLastSession(today));
    session = Session.fromDate(today);
  }

  private boolean isTodaySession(LocalDate today) {
    return session.isSameDay(today);
  }

  private boolean isFirstSession() {
    return session == null;
  }

  private void rotateBoxes(long daysSinceLastSession) {
    if (daysSinceLastSession == 1) {
      orangeBox.moveCardsTo(redBox);
      greenBox.moveCardsTo(orangeBox);
    } else if (daysSinceLastSession > 1) {
      orangeBox.moveCardsTo(redBox);
      greenBox.moveCardsTo(redBox);
    }
  }

  private static List<CardId> toCardIdList(List<String> idList) {
    return idList.stream().map(CardId::new).collect(Collectors.toList());
  }

  public String pickNextCardId(LocalDate today) {
    if (!isTodaySession(today)) {
      throw new SessionNotStartedException(id);
    }

    if (!unplayedCards.isEmpty()) {
      CardId cardId = unplayedCards.get(0);
      return cardId.value();
    }

    return redBox.pickNextCard();
  }

  public void solveCard(LocalDate today, String cardId, String boxName) {
    String pickedCardId = pickNextCardId(today);
    if (!cardId.equals(pickedCardId)) {
      throw new IllegalArgumentException("The card with id " + cardId + " is not the current played card");
    }

    CardId solvedCardId = new CardId(cardId);
    if (!unplayedCards.contains(solvedCardId) && !redBox.containsCard(solvedCardId)) {
      throw new IllegalArgumentException("Card with id " + cardId + " not found in unplayed nor red box");
    }

    if (unplayedCards.contains(solvedCardId)) {
      unplayedCards.remove(solvedCardId);
      placeCardInBox(solvedCardId, boxName);
    } else {
      redBox.removeCard(solvedCardId);
      placeCardInBox(solvedCardId, boxName);
    }
  }

  private void placeCardInBox(CardId cardId, String boxName) {
    Box box = getBoxByName(boxName);
    box.placeCard(cardId);
  }

  private Box getBoxByName(String boxName) {
    if (boxName.equals(GREEN_BOX)) {
      return greenBox;
    }

    if (boxName.equals(ORANGE_BOX)) {
      return orangeBox;
    }

    if (boxName.equals(RED_BOX)) {
      return redBox;
    }

    throw new IllegalArgumentException("Unknown box " + boxName);
  }
}
