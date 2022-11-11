package com.dann41.anki.intrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.dann41.anki.application.cardpicker.CardPicker;
import com.dann41.anki.application.cardsolver.CardSolver;
import com.dann41.anki.application.deckcreator.DeckCreator;
import com.dann41.anki.application.sessionstarter.SessionStarter;
import com.dann41.anki.application.statefinder.StateFinder;
import com.dann41.anki.domain.card.CardRepository;
import com.dann41.anki.domain.deck.DeckRepository;
import com.dann41.anki.intrastructure.presentation.Starter;
import com.dann41.anki.intrastructure.repository.card.FileCardImporter;
import com.dann41.anki.intrastructure.repository.card.FileCardRepository;
import com.dann41.anki.intrastructure.repository.deck.FileDeckRepository;

import java.time.Clock;
import java.util.HashMap;
import java.util.Map;

public class AppContext {

  Map<Class<?>, Object> applicationContext = new HashMap<>();

  public AppContext() {
    registerServices();
    registerRepositories();
    registerUseCases();
    registerPresentation();
  }

  private void registerServices() {
    register(Clock.class, Clock.systemUTC());
    register(ObjectMapper.class, new ObjectMapper().registerModule(new JavaTimeModule()));
  }

  private void registerPresentation() {
    register(Starter.class, new Starter(this));
  }

  private <T> void register(Class<? super T> clazz, T object) {
    applicationContext.put(clazz, object);
  }

  @SuppressWarnings("unchecked")
  public <U, T extends U> T get(Class<U> clazz) {
    T object = (T) applicationContext.get(clazz);
    if (object == null) {
      throw new IllegalStateException("Bean of type " + clazz.getSimpleName() + " not registered");
    }
    return object;
  }

  private void registerRepositories() {
    register(DeckRepository.class, new FileDeckRepository(get(ObjectMapper.class)));
    register(CardRepository.class, new FileCardRepository(new FileCardImporter()));
  }

  private void registerUseCases() {
    register(StateFinder.class, new StateFinder(
       get(DeckRepository.class)
    ));

    register(CardPicker.class, new CardPicker(
        get(DeckRepository.class),
        get(CardRepository.class),
        get(Clock.class)
    ));

    register(CardSolver.class, new CardSolver(
        get(DeckRepository.class),
        get(Clock.class)
    ));

    register(DeckCreator.class, new DeckCreator(
        get(DeckRepository.class),
        get(com.dann41.anki.domain.card.CardRepository.class)
    ));

    register(SessionStarter.class, new SessionStarter(
        get(DeckRepository.class),
        get(Clock.class)
    ));
  }
}

