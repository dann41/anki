package com.dann41.anki.api;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FixtureHelper {

  private static final String PATH = "src/test/resources/";
  public static String file(String resourceFile) {
    try {
      return Files.readString(Paths.get(PATH + resourceFile));
    } catch (IOException e) {
      throw new RuntimeException("Cannot read fixture as string from file " + resourceFile, e);
    }
  }

}
