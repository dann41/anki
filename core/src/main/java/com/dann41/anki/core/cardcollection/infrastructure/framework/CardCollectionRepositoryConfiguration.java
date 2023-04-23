package com.dann41.anki.core.cardcollection.infrastructure.framework;

import com.dann41.anki.core.cardcollection.domain.CardCollectionRepository;
import com.dann41.anki.core.cardcollection.infrastructure.repository.cardcollection.JpaDelegateCardCollectionRepository;
import com.dann41.anki.core.cardcollection.infrastructure.repository.cardcollection.jpa.CardCollectionJpaRepository;
import com.dann41.anki.shared.infrastructure.framework.SystemConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@Import(SystemConfiguration.class)
@EnableJpaRepositories(basePackages = "com.dann41.anki.core.cardcollection.infrastructure.repository.cardcollection.jpa")
@ComponentScan(basePackages = { "com.dann41.anki.core.cardcollection.infrastructure.repository.*" })
@EntityScan("com.dann41.anki.core.cardcollection.infrastructure.repository.*")
public class CardCollectionRepositoryConfiguration {

  @Bean
  CardCollectionRepository cardCollectionRepository(CardCollectionJpaRepository jpaRepository) {
    return new JpaDelegateCardCollectionRepository(jpaRepository);
  }
}
