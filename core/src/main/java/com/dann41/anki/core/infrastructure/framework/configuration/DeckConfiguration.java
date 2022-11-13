package com.dann41.anki.core.infrastructure.framework.configuration;

import com.dann41.anki.core.application.deck.cardpicker.CardPicker;
import com.dann41.anki.core.application.deck.cardsolver.CardSolver;
import com.dann41.anki.core.application.deck.deckcreator.DeckCreator;
import com.dann41.anki.core.application.deck.sessionstarter.SessionStarter;
import com.dann41.anki.core.application.deck.statefinder.StateFinder;
import com.dann41.anki.core.domain.card.CardRepository;
import com.dann41.anki.core.domain.deck.DeckRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.time.Clock;

@Configuration
@Import({SystemConfiguration.class, RepositoryConfiguration.class})
public class DeckConfiguration {

  @Bean
  CardPicker cardPicker(DeckRepository deckRepository, CardRepository cardRepository, Clock clock) {
    return new CardPicker(deckRepository, cardRepository, clock);
  }

  @Bean
  CardSolver cardSolver(DeckRepository deckRepository, Clock clock) {
    return new CardSolver(deckRepository, clock);
  }

  @Bean
  DeckCreator deckCreator(DeckRepository deckRepository, CardRepository cardRepository) {
    return new DeckCreator(deckRepository, cardRepository);
  }

  @Bean
  SessionStarter sessionStarter(DeckRepository deckRepository, Clock clock) {
    return new SessionStarter(deckRepository, clock);
  }

  @Bean
  StateFinder stateFinder(DeckRepository deckRepository) {
    return new StateFinder(deckRepository);
  }


}
