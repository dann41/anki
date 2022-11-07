package com.weekendesk.anki.services;

import com.weekendesk.anki.repositories.SessionRepository;

public class SessionServiceImpl implements SessionService {

    private final DateService dateService;
    private final SessionRepository repository;

    public SessionServiceImpl(DateService dateService, SessionRepository repository) {
        this.dateService = dateService;
        this.repository = repository;
    }

    @Override
    public long getLastSessionStarted() {
        return repository.getLastSessionStarted();
    }

    @Override
    public void startSession() {
        repository.setLastSessionStarted(dateService.currentEpochDays());
    }

    @Override
    public void finishSession() {
        // NO-OP
    }
}
