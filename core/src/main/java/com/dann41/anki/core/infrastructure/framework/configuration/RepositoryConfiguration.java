package com.dann41.anki.core.infrastructure.framework.configuration;

import com.dann41.anki.core.domain.card.CardRepository;
import com.dann41.anki.core.domain.deck.DeckRepository;
import com.dann41.anki.core.infrastructure.repository.card.FileCardImporter;
import com.dann41.anki.core.infrastructure.repository.card.FileCardRepository;
import com.dann41.anki.core.infrastructure.repository.deck.FileDeckRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(SystemConfiguration.class)
public class RepositoryConfiguration {

  @Bean
  DeckRepository deckRepository(ObjectMapper objectMapper) {
    return new FileDeckRepository(objectMapper);
  }

  @Bean
  CardRepository cardRepository() {
    return new FileCardRepository(
        new FileCardImporter()
    );
  }
}
