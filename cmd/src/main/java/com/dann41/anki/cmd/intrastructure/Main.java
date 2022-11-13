package com.dann41.anki.cmd.intrastructure;

import com.dann41.anki.cmd.intrastructure.presentation.Starter;
import com.dann41.anki.core.infrastructure.framework.configuration.CoreModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
    scanBasePackageClasses = {CoreModule.class, Starter.class}
)
public class Main {

  public static void main(String[] args) {
    var context = SpringApplication.run(Main.class, args);

    String deckId = "arts";
    String collectionId = "arts";
    context.getBean(Starter.class).start(deckId, collectionId);
  }
}
