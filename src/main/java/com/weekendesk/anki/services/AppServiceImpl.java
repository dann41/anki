package com.weekendesk.anki.services;

import com.weekendesk.anki.domain.AppState;
import com.weekendesk.anki.domain.Box;
import com.weekendesk.anki.repositories.AppRepository;

public class AppServiceImpl implements AppService {

    private final AppRepository repository;
    private final CardService cardService;
    private final SessionService sessionService;
    private final DateService dateService;

    public AppServiceImpl(CardService cardService,
                          SessionService sessionService,
                          DateService dateService,
                          AppRepository repository) {
        this.cardService = cardService;
        this.sessionService = sessionService;
        this.dateService = dateService;
        this.repository = repository;
    }

    @Override
    public void loadState() {
        repository.load();

        long daysWithoutTraining = Math.min(2, dateService.diffInEpochDays(sessionService.getLastSessionStarted()));
        for (int i = 0; i < daysWithoutTraining; i++) {
            cardService.rotateCards();
        }
    }

    @Override
    public void saveState() {
        repository.save();
    }

    @Override
    public AppState getState() {
        return new AppState(
                cardService.countCards(Box.GREEN),
                cardService.countCards(Box.ORANGE),
                cardService.countCards(Box.RED),
                sessionService.getLastSessionStarted()
        );
    }

    @Override
    public boolean hasAnyCard() {
        AppState state = getState();
        return state.cardsInGreen + state.cardsInOrange + state.cardsInRed > 0;
    }

    public boolean isTrainingComplete() {
        AppState state = getState();
        return state.cardsInGreen != 0 && state.cardsInOrange == 0 && state.cardsInRed == 0;
    }

    @Override
    public boolean hasTrainedToday() {
        long lastSessionStarted = sessionService.getLastSessionStarted();
        return dateService.diffInEpochDays(lastSessionStarted) == 0;
    }
}
