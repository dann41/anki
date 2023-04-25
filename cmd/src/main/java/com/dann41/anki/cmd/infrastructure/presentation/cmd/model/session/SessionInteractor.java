package com.dann41.anki.cmd.infrastructure.presentation.cmd.model.session;

import com.dann41.anki.core.user.application.authenticator.AuthenticateUserCommand;
import com.dann41.anki.core.user.application.userfinder.FindUserByUsernameQuery;
import com.dann41.anki.core.user.application.userfinder.FindUserResponse;
import com.dann41.anki.shared.application.CommandBus;
import com.dann41.anki.shared.application.QueryBus;
import org.springframework.stereotype.Service;

@Service
public class SessionInteractor {
    private final CommandBus commandBus;
    private final QueryBus queryBus;

    private Session session;
    private String deckId;

    public SessionInteractor(CommandBus commandBus, QueryBus queryBus) {
        this.commandBus = commandBus;
        this.queryBus = queryBus;
    }

    public void login(String username, String password) {
        commandBus.publish(new AuthenticateUserCommand(username, password));
        FindUserResponse user = queryBus.publish(new FindUserByUsernameQuery(username));
        this.session = new Session(
                user.id(),
                user.username()
        );
    }

    public Session currentSession() {
        return session;
    }

    public void logout() {
        session = null;
    }

    public void selectDeck(String deckId) {
        this.deckId = deckId;
    }

    public void endDeck() {
        this.deckId = null;
    }

    public String deckSelected() {
        return deckId;
    }
}
