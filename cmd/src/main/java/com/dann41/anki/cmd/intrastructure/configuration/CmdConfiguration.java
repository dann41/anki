package com.dann41.anki.cmd.intrastructure.configuration;

import com.dann41.anki.cmd.intrastructure.presentation.cmd.core.Navigator;
import com.dann41.anki.cmd.intrastructure.services.FileCollectionImporter;
import com.dann41.anki.cmd.intrastructure.services.Starter;
import com.dann41.anki.core.cardcollection.application.collectionsimporter.CardCollectionsImporter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@ComponentScan(basePackages = "com.dann41.anki.cmd.intrastructure.presentation")
public class CmdConfiguration {

  @Lazy
  @Bean
  Starter starter(Navigator navigator) {
    return new Starter(navigator);
  }

  @Bean
  FileCollectionImporter fileCollectionImporter(CardCollectionsImporter cardCollectionsImporter) {
    return new FileCollectionImporter(cardCollectionsImporter);
  }
}
