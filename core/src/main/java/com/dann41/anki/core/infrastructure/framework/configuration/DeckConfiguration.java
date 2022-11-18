package com.dann41.anki.core.infrastructure.framework.configuration;

import com.dann41.anki.core.application.deck.alldecksfinder.AllDecksFinder;
import com.dann41.anki.core.application.deck.cardpicker.CardPicker;
import com.dann41.anki.core.application.deck.cardsolver.CardSolver;
import com.dann41.anki.core.application.deck.deckcreator.DeckCreator;
import com.dann41.anki.core.application.deck.deckremover.DeckDeleter;
import com.dann41.anki.core.application.deck.sessionstarter.SessionStarter;
import com.dann41.anki.core.application.deck.statefinder.StateFinder;
import com.dann41.anki.core.domain.cardcollection.CardCollectionRepository;
import com.dann41.anki.core.domain.deck.DeckFinder;
import com.dann41.anki.core.domain.deck.DeckRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.time.Clock;

@Configuration
@Import({SystemConfiguration.class, RepositoryConfiguration.class})
public class DeckConfiguration {

  @Bean
  CardPicker cardPicker(DeckRepository deckRepository, Clock clock) {
    return new CardPicker(deckRepository, clock);
  }

  @Bean
  CardSolver cardSolver(DeckRepository deckRepository, Clock clock) {
    return new CardSolver(deckRepository, clock);
  }

  @Bean
  DeckCreator deckCreator(DeckRepository deckRepository, CardCollectionRepository collectionRepository) {
    return new DeckCreator(deckRepository, collectionRepository);
  }

  @Bean
  SessionStarter sessionStarter(DeckRepository deckRepository, Clock clock) {
    return new SessionStarter(deckRepository, clock);
  }

  @Bean
  StateFinder stateFinder(DeckRepository deckRepository) {
    return new StateFinder(deckRepository);
  }

  @Bean
  DeckFinder deckFinder(DeckRepository deckRepository) {
    return new DeckFinder(deckRepository);
  }

  @Bean
  AllDecksFinder allDecksFinder(DeckRepository deckRepository) {
    return new AllDecksFinder(deckRepository);
  }

  @Bean
  DeckDeleter deckDeleter(DeckRepository deckRepository) {
    return new DeckDeleter(deckRepository);
  }

}
