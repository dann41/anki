package com.dann41.anki.core.deck.infrastructure.framework;

import com.dann41.anki.core.cardcollection.domain.CardCollectionRepository;
import com.dann41.anki.core.deck.application.alldecksfinder.MyDecksFinder;
import com.dann41.anki.core.deck.application.alldecksfinder.MyDecksFinderQueryHandler;
import com.dann41.anki.core.deck.application.cardpicker.CardPicker;
import com.dann41.anki.core.deck.application.cardpicker.CardPickerQueryHandler;
import com.dann41.anki.core.deck.application.cardsolver.CardSolver;
import com.dann41.anki.core.deck.application.cardsolver.SolveCardCommandHandler;
import com.dann41.anki.core.deck.application.deckcreator.CreateDeckCommandHandler;
import com.dann41.anki.core.deck.application.deckcreator.DeckCreator;
import com.dann41.anki.core.deck.application.deckremover.DeckDeleter;
import com.dann41.anki.core.deck.application.deckremover.DeleteDeckCommandHandler;
import com.dann41.anki.core.deck.application.sessionstarter.SessionStarter;
import com.dann41.anki.core.deck.application.sessionstarter.StartSessionCommandHandler;
import com.dann41.anki.core.deck.application.statefinder.FindStatusQueryHandler;
import com.dann41.anki.core.deck.application.statefinder.StatusFinder;
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
  StatusFinder stateFinder(DeckRepository deckRepository) {
    return new StatusFinder(deckRepository);
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


  @Bean
  MyDecksFinderQueryHandler myDecksFinderQueryHandler(MyDecksFinder myDecksFinder) {
    return new MyDecksFinderQueryHandler(myDecksFinder);
  }

  @Bean
  CardPickerQueryHandler cardPickerQueryHandler(CardPicker cardPicker) {
    return new CardPickerQueryHandler(cardPicker);
  }

  @Bean
  SolveCardCommandHandler solveCardCommandHandler(CardSolver cardSolver) {
    return new SolveCardCommandHandler(cardSolver);
  }

  @Bean
  CreateDeckCommandHandler createDeckCommandHandler(DeckCreator deckCreator) {
    return new CreateDeckCommandHandler(deckCreator);
  }

  @Bean
  DeleteDeckCommandHandler deleteDeckCommandHandler(DeckDeleter deckDeleter) {
    return new DeleteDeckCommandHandler(deckDeleter);
  }

  @Bean
  StartSessionCommandHandler startSessionCommandHandler(SessionStarter sessionStarter) {
    return new StartSessionCommandHandler(sessionStarter);
  }

  @Bean
  FindStatusQueryHandler findStatusQueryHandler(StatusFinder statusFinder) {
    return new FindStatusQueryHandler(statusFinder);
  }
}
