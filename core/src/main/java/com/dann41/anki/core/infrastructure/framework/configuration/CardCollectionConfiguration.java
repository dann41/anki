package com.dann41.anki.core.infrastructure.framework.configuration;

import com.dann41.anki.core.application.collection.allcollectionsfinder.AllCollectionsFinder;
import com.dann41.anki.core.domain.cardcollection.CardCollectionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CardCollectionConfiguration {

  @Bean
  public AllCollectionsFinder allCollectionsFinder(CardCollectionRepository cardCollectionRepository) {
    return new AllCollectionsFinder(cardCollectionRepository);
  }

}
