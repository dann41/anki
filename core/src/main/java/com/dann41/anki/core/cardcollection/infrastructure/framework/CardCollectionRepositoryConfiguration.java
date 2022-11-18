package com.dann41.anki.core.cardcollection.infrastructure.framework;

import com.dann41.anki.core.cardcollection.domain.CardCollectionRepository;
import com.dann41.anki.core.cardcollection.infrastructure.repository.cardcollection.FileCardImporter;
import com.dann41.anki.core.cardcollection.infrastructure.repository.cardcollection.FileCardCollectionRepository;
import com.dann41.anki.core.shared.infrastructure.framework.SystemConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(SystemConfiguration.class)
public class CardCollectionRepositoryConfiguration {

  @Bean
  CardCollectionRepository cardCollectionRepository() {
    return new FileCardCollectionRepository(
        new FileCardImporter()
    );
  }
}
