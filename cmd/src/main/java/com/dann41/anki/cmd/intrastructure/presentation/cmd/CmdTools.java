package com.dann41.anki.cmd.intrastructure.presentation.cmd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CmdTools {

  private final BufferedReader reader;

  public CmdTools() {
    this.reader = new BufferedReader(new InputStreamReader(System.in));
  }

  public String readLine() {
    try {
      return reader.readLine();
    } catch (IOException e) {
      return null;
    }
  }

  public void close() {
    try {
      reader.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
