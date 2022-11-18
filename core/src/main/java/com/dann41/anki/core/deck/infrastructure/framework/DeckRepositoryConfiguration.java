package com.dann41.anki.core.deck.infrastructure.framework;

import com.dann41.anki.core.deck.domain.DeckRepository;
import com.dann41.anki.core.deck.infrastructure.repository.FileDeckRepository;
import com.dann41.anki.core.shared.infrastructure.framework.SystemConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(SystemConfiguration.class)
public class DeckRepositoryConfiguration {
  @Bean
  DeckRepository deckRepository(ObjectMapper objectMapper) {
    return new FileDeckRepository(objectMapper);
  }
}
