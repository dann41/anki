package com.dann41.anki.core.cardcollection.infrastructure.framework;

import com.dann41.anki.core.cardcollection.application.allcollectionsfinder.AllCollectionsFinder;
import com.dann41.anki.core.cardcollection.domain.CardCollectionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CardCollectionConfiguration {

  @Bean
  public AllCollectionsFinder allCollectionsFinder(CardCollectionRepository cardCollectionRepository) {
    return new AllCollectionsFinder(cardCollectionRepository);
  }

}
