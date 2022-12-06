package com.dann41.anki.cmd.intrastructure.presentation.cmd;

import java.util.List;

public record CmdMenuItem(String message, List<String> options) {

  public static CmdMenuItem of(String message, String... options) {
    return new CmdMenuItem(message, List.of(options));
  }

  public boolean respondToInput(String input) {
    return options.contains(input);
  }
}
