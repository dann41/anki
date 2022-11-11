package com.dann41.anki.application.cardpicker;

public class CardResponse {

  private final String id;
  private final String question;
  private final String answer;

  public CardResponse(String id, String question, String answer) {
    this.id = id;
    this.question = question;
    this.answer = answer;
  }

  public String id() {
    return id;
  }

  public String question() {
    return question;
  }

  public String answer() {
    return answer;
  }
}
