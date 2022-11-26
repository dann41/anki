package com.dann41.anki.cmd.intrastructure.configuration;

import com.dann41.anki.cmd.intrastructure.services.FileCollectionImporter;
import com.dann41.anki.cmd.intrastructure.services.Starter;
import com.dann41.anki.core.cardcollection.application.collectionsimporter.CardCollectionsImporter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class CmdConfiguration {

  @Lazy
  @Bean
  Starter starter(ApplicationContext applicationContext) {
    return new Starter(applicationContext);
  }

  @Bean
  FileCollectionImporter fileCollectionImporter(CardCollectionsImporter cardCollectionsImporter) {
    return new FileCollectionImporter(cardCollectionsImporter);
  }
}
