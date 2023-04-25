package com.dann41.anki.cmd.infrastructure.presentation.cmd;

import java.util.List;

public record CmdMenu(
    String title,
    List<CmdMenuItem> items,
    String question
) {
  public CmdMenuItem findItemForInput(String input) {
    return items().stream()
        .filter(item -> item.respondToInput(input))
        .findFirst()
        .orElse(null);
  }
}
