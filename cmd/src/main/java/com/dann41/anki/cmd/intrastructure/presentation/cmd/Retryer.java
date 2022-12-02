package com.dann41.anki.cmd.intrastructure.presentation.cmd;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public final class Retryer {

  private static final int DEFAULT_MAX_TRIES = 3;

  private final int maxTries;
  private int triesLeft = 0;

  public Retryer() {
    this(DEFAULT_MAX_TRIES);
  }

  public Retryer(int maxTries) {
    this.maxTries = maxTries;
    reset();
  }

  public void reset() {
    this.triesLeft = maxTries;
  }

  public boolean retry() {
    try {
      retry(null);
      return true;
    } catch (RetriesExhaustedException e) {
      return false;
    }
  }

  public void retry(Runnable actionToTry) {
    if (triesLeft > 0) {
      triesLeft--;
      Optional.ofNullable(actionToTry).ifPresent(Runnable::run);
    } else {
      throw new RetriesExhaustedException();
    }
  }
}
