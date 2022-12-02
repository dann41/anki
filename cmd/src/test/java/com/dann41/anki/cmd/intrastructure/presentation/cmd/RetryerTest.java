package com.dann41.anki.cmd.intrastructure.presentation.cmd;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RetryerTest {

  @Test
  public void shouldReturnTrueUntilMaxTriesReached() {
    var retries = new Retryer(2);

    assertThat(retries.retry()).isTrue();
    assertThat(retries.retry()).isTrue();
    assertThat(retries.retry()).isFalse();
  }

  @Test
  public void shouldReturnTrueWhenNoMoreTries() {
    var retries = new Retryer(1);

    assertThat(retries.retry()).isTrue();
  }

  @Test
  public void shouldCallActionWhenRemainingTries() {
    var retries = new Retryer(2);
    AtomicInteger i = new AtomicInteger(0);
    Runnable action = i::getAndIncrement;

    retries.retry(action);

    assertThat(i.get()).isEqualTo(1);
  }

  @Test
  public void shouldCallCallbackWhenMaxTriesReached() {
    var retries = new Retryer(0);

    assertThatThrownBy(() -> retries.retry(() -> {}))
        .isInstanceOf(RetriesExhaustedException.class);
  }

}