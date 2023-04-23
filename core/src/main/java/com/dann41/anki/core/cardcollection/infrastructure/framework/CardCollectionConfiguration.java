package com.dann41.anki.core.cardcollection.infrastructure.framework;

import com.dann41.anki.core.cardcollection.application.allcollectionsfinder.AllCollectionsFinder;
import com.dann41.anki.core.cardcollection.application.allcollectionsfinder.AllCollectionsQueryHandler;
import com.dann41.anki.core.cardcollection.application.collectionsimporter.CardCollectionsImporter;
import com.dann41.anki.core.cardcollection.application.collectionsimporter.ImportCollectionsCommandHandler;
import com.dann41.anki.core.cardcollection.domain.CardCollectionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CardCollectionConfiguration {

  @Bean
  public AllCollectionsFinder allCollectionsFinder(CardCollectionRepository cardCollectionRepository) {
    return new AllCollectionsFinder(cardCollectionRepository);
  }

  @Bean
  public CardCollectionsImporter collectionImporter(CardCollectionRepository cardCollectionRepository) {
    return new CardCollectionsImporter(cardCollectionRepository);
  }

  @Bean
  public ImportCollectionsCommandHandler importCollectionsCommandHandler(
          CardCollectionsImporter cardCollectionsImporter
  ) {
    return new ImportCollectionsCommandHandler(cardCollectionsImporter);
  }

  @Bean
  public AllCollectionsQueryHandler allCollectionsQueryHandler(
          AllCollectionsFinder allCollectionsFinder
  ) {
    return new AllCollectionsQueryHandler(allCollectionsFinder);
  }
}
