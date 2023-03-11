package com.dann41.anki.api;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

public class MutableClock extends Clock {

  private Clock clock;

  public MutableClock(Clock clock) {
    this.clock = clock;
  }

  public void replaceWith(Clock clock) {
    if (clock == this) {
      throw new IllegalArgumentException("Cannot replace with itself");
    }
    this.clock = clock;
  }

  public Clock delegate() {
    return clock;
  }

  @Override
  public ZoneId getZone() {
    return clock.getZone();
  }

  @Override
  public Clock withZone(ZoneId zone) {
    return clock.withZone(zone);
  }

  @Override
  public Instant instant() {
    return clock.instant();
  }
}
