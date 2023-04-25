package com.dann41.anki.core.deck.application.sessionstarter;

import com.dann41.anki.shared.application.CommandHandler;

public class StartSessionCommandHandler implements CommandHandler<StartSessionCommand> {
    private final SessionStarter sessionStarter;

    public StartSessionCommandHandler(SessionStarter sessionStarter) {
        this.sessionStarter = sessionStarter;
    }

    @Override
    public void handle(StartSessionCommand command) {
        sessionStarter.execute(command);
    }

    @Override
    public Class<StartSessionCommand> supports() {
        return StartSessionCommand.class;
    }
}
