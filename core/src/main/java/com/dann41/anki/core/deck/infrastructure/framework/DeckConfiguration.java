package com.dann41.anki.core.deck.infrastructure.framework;

import com.dann41.anki.core.cardcollection.domain.CardCollectionRepository;
import com.dann41.anki.core.deck.application.alldecksfinder.MyDecksFinder;
import com.dann41.anki.core.deck.application.cardpicker.CardPicker;
import com.dann41.anki.core.deck.application.cardsolver.CardSolver;
import com.dann41.anki.core.deck.application.deckcreator.DeckCreator;
import com.dann41.anki.core.deck.application.deckremover.DeckDeleter;
import com.dann41.anki.core.deck.application.sessionstarter.SessionStarter;
import com.dann41.anki.core.deck.application.statefinder.StateFinder;
import com.dann41.anki.core.deck.domain.DeckFinder;
import com.dann41.anki.core.deck.domain.DeckRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
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
  MyDecksFinder allDecksFinder(DeckRepository deckRepository) {
    return new MyDecksFinder(deckRepository);
  }

  @Bean
  DeckDeleter deckDeleter(DeckRepository deckRepository) {
    return new DeckDeleter(deckRepository);
  }

}
