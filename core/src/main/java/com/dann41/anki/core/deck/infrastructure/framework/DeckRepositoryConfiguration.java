package com.dann41.anki.core.deck.infrastructure.framework;

import com.dann41.anki.core.deck.domain.DeckRepository;
import com.dann41.anki.core.deck.infrastructure.repository.JpaDelegateDeckRepository;
import com.dann41.anki.core.deck.infrastructure.repository.jpa.DeckJpaRepository;
import com.dann41.anki.core.shared.infrastructure.framework.SystemConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@Import(SystemConfiguration.class)
@EnableJpaRepositories(basePackages = "com.dann41.anki.core.deck.infrastructure.repository.jpa")
@ComponentScan(basePackages = { "com.dann41.anki.core.deck.infrastructure.repository.*" })
@EntityScan("com.dann41.anki.core.deck.infrastructure.repository.*")
public class DeckRepositoryConfiguration {
  @Bean
  DeckRepository deckRepository(DeckJpaRepository deckJpaRepository) {
    return new JpaDelegateDeckRepository(deckJpaRepository);
  }
}
