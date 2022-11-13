package com.dann41.anki.core.domain.deck;

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
  private final Questions questions;
  private final List<QuestionId> nonAnsweredCards;
  private final Box redBox;
  private final Box orangeBox;
  private final Box greenBox;
  private Session session;

  public Deck(DeckId id, List<Question> question, List<QuestionId> nonAnsweredCards, Box redBox, Box orangeBox, Box greenBox, Session session) {
    this.id = id;
    this.questions = new Questions(question);
    this.nonAnsweredCards = nonAnsweredCards;
    this.redBox = redBox;
    this.orangeBox = orangeBox;
    this.greenBox = greenBox;
    this.session = session;
  }

  public static Deck create(String deckId, List<Question> cards) {
    return new Deck(
        new DeckId(deckId),
        cards,
        toQuestionIdFromQuestion(cards),
        Box.create(Collections.emptyList()),
        Box.create(Collections.emptyList()),
        Box.create(Collections.emptyList()),
        null
    );
  }

  public static Deck restore(
      String deckId,
      List<Question> questions,
      List<String> nonPlayedCards,
      List<String> cardsInRedBox,
      List<String> cardsInOrangeBox,
      List<String> cardsInGreenBox,
      LocalDate lastSession
  ) {
    return new Deck(
        new DeckId(deckId),
        questions,
        toQuestionIdList(nonPlayedCards),
        Box.create(cardsInRedBox),
        Box.create(cardsInOrangeBox),
        Box.create(cardsInGreenBox),
        Session.fromDate(lastSession)
    );
  }

  private static List<QuestionId> toQuestionIdFromQuestion(List<Question> questions) {
    return questions.stream()
        .map(question -> new QuestionId(question.question()))
        .collect(Collectors.toList());
  }

  private static List<QuestionId> toQuestionIdList(List<String> questionIds) {
    return questionIds.stream().map(QuestionId::new).collect(Collectors.toList());
  }

  public DeckId id() {
    return id;
  }

  public List<Question> questions() {
    return questions.value();
  }

  public List<String> unansweredCards() {
    return nonAnsweredCards.stream().map(QuestionId::value).collect(Collectors.toList());
  }

  public List<String> cardsInRedBox() {
    return redBox.pullAllCards().stream().map(QuestionId::value).collect(Collectors.toList());
  }

  public List<String> cardsInOrangeBox() {
    return orangeBox.pullAllCards().stream().map(QuestionId::value).collect(Collectors.toList());
  }

  public List<String> cardsInGreenBox() {
    return greenBox.pullAllCards().stream().map(QuestionId::value).collect(Collectors.toList());
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

  public String pickNextCardId(LocalDate today) {
    if (!isTodaySession(today)) {
      throw new SessionNotStartedException(id);
    }

    if (!nonAnsweredCards.isEmpty()) {
      QuestionId questionId = nonAnsweredCards.get(0);
      return questionId.value();
    }

    return redBox.pickNextCard();
  }

  public void solveCard(LocalDate today, String cardId, String boxName) {
    String pickedCardId = pickNextCardId(today);
    if (!cardId.equals(pickedCardId)) {
      throw new IllegalArgumentException("The card with id " + cardId + " is not the current played card");
    }

    QuestionId solvedQuestionId = new QuestionId(cardId);
    if (!nonAnsweredCards.contains(solvedQuestionId) && !redBox.containsCard(solvedQuestionId)) {
      throw new IllegalArgumentException("Card with id " + cardId + " not found in unplayed nor red box");
    }

    if (nonAnsweredCards.contains(solvedQuestionId)) {
      nonAnsweredCards.remove(solvedQuestionId);
      placeCardInBox(solvedQuestionId, boxName);
    } else {
      redBox.removeCard(solvedQuestionId);
      placeCardInBox(solvedQuestionId, boxName);
    }
  }

  private void placeCardInBox(QuestionId questionId, String boxName) {
    Box box = getBoxByName(boxName);
    box.placeCard(questionId);
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
