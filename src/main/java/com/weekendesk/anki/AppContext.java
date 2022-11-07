package com.weekendesk.anki;

import com.weekendesk.anki.helpers.ConsoleReaderHelper;
import com.weekendesk.anki.helpers.ReaderHelper;
import com.weekendesk.anki.repositories.AnkiRepository;
import com.weekendesk.anki.services.*;

public class AppContext {

    // Repositories
    private AnkiRepository repository;

    // Services
    private CardImportService cardImportService;
    private CardService cardService;
    private SessionService sessionService;
    private AppService appService;

    // Helpers
    private ReaderHelper readerHelper;

    public AppContext() {
        initRepositories();
        initServices();
        initHelpers();
    }

    private void initRepositories() {
        repository = new AnkiRepository();
    }

    private void initServices() {
        DateService dateService = new DateServiceImpl();
        cardService = new CardServiceImpl(repository);
        cardImportService = new FileCardImportService(cardService);
        sessionService = new SessionServiceImpl(dateService, repository);
        appService = new AppServiceImpl(cardService, sessionService, dateService, repository);
    }

    private void initHelpers() {
        readerHelper = new ConsoleReaderHelper();
    }

    public CardImportService getCardImportService() {
        return cardImportService;
    }

    public CardService getCardService() {
        return cardService;
    }

    public SessionService getSessionService() {
        return sessionService;
    }

    public AppService getAppService() {
        return appService;
    }

    public ReaderHelper getReaderHelper() {
        return readerHelper;
    }
}

