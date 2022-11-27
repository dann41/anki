package com.dann41.anki.core.deck.domain;

import com.dann41.anki.core.cardcollection.domain.CardCollectionId;
import com.dann41.anki.core.user.domain.UserId;

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

  private final CardCollectionId collectionId;
  private final UserId userId;
  private final List<QuestionId> nonAnsweredCards;
  private final Box redBox;
  private final Box orangeBox;
  private final Box greenBox;
  private Session session;

  private boolean isDeleted;

  public Deck(DeckId id, UserId userId, String collectionId, List<Question> question, List<QuestionId> nonAnsweredCards, Box redBox, Box orangeBox, Box greenBox, Session session, boolean isDeleted) {
    this.id = id;
    this.userId = userId;
    this.collectionId = new CardCollectionId(collectionId);
    this.questions = new Questions(question);
    this.nonAnsweredCards = nonAnsweredCards;
    this.redBox = redBox;
    this.orangeBox = orangeBox;
    this.greenBox = greenBox;
    this.session = session;
    this.isDeleted = isDeleted;
  }

  public static Deck create(String deckId, String userId, String collectionId, List<Question> cards) {
    return new Deck(
        new DeckId(deckId),
        new UserId(userId),
        collectionId,
        cards,
        toQuestionIdFromQuestion(cards),
        Box.create(Collections.emptyList()),
        Box.create(Collections.emptyList()),
        Box.create(Collections.emptyList()),
        null,
        false
    );
  }

  public static Deck restore(
      String deckId,
      String userId,
      String collectionId,
      List<Question> questions,
      List<String> nonPlayedCards,
      List<String> cardsInRedBox,
      List<String> cardsInOrangeBox,
      List<String> cardsInGreenBox,
      LocalDate lastSession,
      Boolean isDeleted
  ) {
    return new Deck(
        new DeckId(deckId),
        new UserId(userId),
        collectionId,
        questions,
        toQuestionIdList(nonPlayedCards),
        Box.create(cardsInRedBox),
        Box.create(cardsInOrangeBox),
        Box.create(cardsInGreenBox),
        Session.fromDate(lastSession),
        isDeleted
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

  public UserId userId() {
    return userId;
  }

  public String collectionId() {
    return collectionId.value();
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

  public boolean isDeleted() {
    return isDeleted;
  }

  public void startNewSession(LocalDate today) {
    ensureNotDeleted();

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

  public Question pickNextQuestion(LocalDate today) {
    ensureNotDeleted();

    if (!isTodaySession(today)) {
      throw new SessionNotStartedException(id);
    }

    if (!nonAnsweredCards.isEmpty()) {
      QuestionId questionId = nonAnsweredCards.get(0);
      return questions.findById(questionId);
    }

    return pickNextQuestionFromRedBox();
  }

  public void solveCard(LocalDate today, String cardId, String boxName) {
    ensureNotDeleted();

    Question nextQuestion = pickNextQuestion(today);
    if (!cardId.equals(nextQuestion.id())) {
      throw new IllegalArgumentException("The card with id " + cardId + " is not the current question");
    }

    QuestionId solvedQuestionId = new QuestionId(cardId);
    if (!nonAnsweredCards.contains(solvedQuestionId) && !redBox.containsCard(solvedQuestionId)) {
      throw new IllegalArgumentException("Card with id " + cardId + " not found in unanswered nor red box");
    }

    if (nonAnsweredCards.contains(solvedQuestionId)) {
      nonAnsweredCards.remove(solvedQuestionId);
      placeCardInBox(solvedQuestionId, boxName);
    } else {
      redBox.removeCard(solvedQuestionId);
      placeCardInBox(solvedQuestionId, boxName);
    }
  }

  public void delete() {
    if (isDeleted) {
      throw new IllegalArgumentException("Deck "+ id + " already deleted");
    }

    isDeleted = true;
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

  private Question pickNextQuestionFromRedBox() {
    String id = redBox.pickNextCard();
    if (id == null) {
      return null;
    }
    return questions.findById(new QuestionId(id));
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

    throw new IllegalArgumentException("Unknown box " + boxName + " on deck " + id);
  }

  private void ensureNotDeleted() {
    if (isDeleted) {
      throw new IllegalArgumentException("Cannot run operation on deleted deck " + id);
    }
  }
}
