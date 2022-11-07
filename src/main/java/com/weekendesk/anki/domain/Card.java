package com.weekendesk.anki.domain;

public class Card {

    private String question;
    private String answer;
    private Box box;

    public Card() {
        box = Box.RED;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Box getBox() {
        return box;
    }

    public void setBox(Box box) {
        this.box = box;
    }

    @Override
    public boolean equals(Object o) {
        // NOTE that box is not part of equals or hashcode!
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (question != null ? !question.equals(card.question) : card.question != null) return false;
        return answer != null ? answer.equals(card.answer) : card.answer == null;

    }

    @Override
    public int hashCode() {
        int result = question != null ? question.hashCode() : 0;
        result = 31 * result + (answer != null ? answer.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Card{" +
                "answer='" + answer + '\'' +
                ", question='" + question + '\'' +
                '}';
    }

}
