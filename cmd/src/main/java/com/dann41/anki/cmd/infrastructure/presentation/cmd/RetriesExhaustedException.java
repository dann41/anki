package com.dann41.anki.cmd.infrastructure.presentation.cmd;

public class RetriesExhaustedException extends RuntimeException {
  public RetriesExhaustedException() {
    super("No more tries left. Reset the retryer and try again");
  }
}
