package com.dann41.anki.cmd.infrastructure.presentation.cmd;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class CmdTools {

  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_BLACK = "\u001B[30m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_YELLOW = "\u001B[33m";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_PURPLE = "\u001B[35m";
  public static final String ANSI_CYAN = "\u001B[36m";
  public static final String ANSI_WHITE = "\u001B[37m";

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

  public void printInfo(String message) {
    System.out.println(ANSI_BLUE + "✅️ " + message + ANSI_RESET);
  }

  public void printError(String message) {
    System.out.println(ANSI_RED + "❌ " + message + ANSI_RESET);
  }

  public String printMenu(CmdMenu menu) {
    return printMenuWithRetries(menu, 3);
  }

  public String printMenuWithRetries(CmdMenu menu, int maxTries) {
    var retryer = new Retryer(maxTries);

    while (retryer.retry()) {
      System.out.println(menu.title());
      menu.items().forEach(item -> System.out.println(item.message()));
      System.out.println();
      System.out.print(menu.question());
      String input = readLine();

      CmdMenuItem selectedItem = menu.findItemForInput(input);
      if (selectedItem != null) {
        return input;
      }

      printError("Invalid option: " + input);
    }

    printError("Too many retries. Giving up...");

    return "";
  }

  public void close() {
    try {
      reader.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
