package com.dann41.anki.core.domain.deck;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Session {
  private final LocalDate lastSession;

  public Session(LocalDate lastSession) {
    if (lastSession == null) {
      throw new IllegalArgumentException("Session cannot have a null date");
    }
    this.lastSession = lastSession;
  }

  public static Session fromDate(LocalDate lastSession) {
    return lastSession != null ? new Session(lastSession) : null;
  }

  public LocalDate value() {
    return lastSession;
  }

  public boolean isSameDay(LocalDate today) {
    return lastSession.isEqual(today);
  }

  public long daysSinceLastSession(LocalDate today) {
    return ChronoUnit.DAYS.between(lastSession, today);
  }
}
