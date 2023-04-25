package com.dann41.anki.cmd.infrastructure.presentation.cmd.deckplayer;

import com.dann41.anki.cmd.infrastructure.presentation.cmd.AnkiState;
import com.dann41.anki.cmd.infrastructure.presentation.cmd.ViewContext;
import com.dann41.anki.cmd.infrastructure.presentation.cmd.core.BasePresenter;
import com.dann41.anki.cmd.infrastructure.presentation.cmd.model.session.SessionInteractor;
import com.dann41.anki.core.deck.application.cardpicker.CardPickerQuery;
import com.dann41.anki.core.deck.application.cardpicker.CardPickerResponse;
import com.dann41.anki.core.deck.application.cardsolver.SolveCardCommand;
import com.dann41.anki.core.deck.application.sessionstarter.StartSessionCommand;
import com.dann41.anki.core.deck.application.statefinder.FindStatusQuery;
import com.dann41.anki.core.deck.application.statefinder.FindStatusResponse;
import com.dann41.anki.core.deck.domain.DeckNotFoundException;
import com.dann41.anki.shared.application.CommandBus;
import com.dann41.anki.shared.application.QueryBus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
public class DeckPlayerPresenter
        extends BasePresenter<DeckPlayerContract.View>
        implements DeckPlayerContract.Presenter {
    private static final Map<String, String> BOX_MAPPER = new HashMap<>();

    static {
        BOX_MAPPER.put("g", "green");
        BOX_MAPPER.put("o", "orange");
        BOX_MAPPER.put("r", "red");
    }

    private final CommandBus commandBus;
    private final QueryBus queryBus;
    private final SessionInteractor sessionInteractor;

    private final ViewContext viewContext = new ViewContext();

    public DeckPlayerPresenter(
            CommandBus commandBus,
            QueryBus queryBus,
            SessionInteractor sessionInteractor
    ) {
        this.commandBus = commandBus;
        this.queryBus = queryBus;
        this.sessionInteractor = sessionInteractor;
    }

    @Override
    public void playDeck() {
        loadViewContext();

        startSession();
        displayState();
        displayNextCard();
    }

    @Override
    public void solveCard(String cardId, String box) {
        loadViewContext();

        String boxName = BOX_MAPPER.get(box.toLowerCase(Locale.getDefault()));
        if (boxName == null) {
            view.displayWrongBoxName(cardId, box);
            return;
        }

        commandBus.publish(new SolveCardCommand(viewContext.currentDeckId(), viewContext.userId(), cardId, boxName));

        displayNextCard();
    }

    private void startSession() {
        var deckId = viewContext.currentDeckId();
        try {
            commandBus.publish(new StartSessionCommand(deckId, viewContext.userId()));
            viewContext.playDeck(deckId);
        } catch (DeckNotFoundException e) {
            view.displayDeckNotFound(deckId);
            navigator.back();
        }
    }

    private AnkiState retrieveState() {
        FindStatusResponse findStatusResponse = queryBus.publish(new FindStatusQuery(
                viewContext.currentDeckId(),
                viewContext.userId()
        ));
        return toAnkiState(findStatusResponse);
    }

    private void displayState() {
        view.displayState(retrieveState());
    }

    private void displayNextCard() {
        CardPickerResponse nextCard = queryBus.publish(
                new CardPickerQuery(viewContext.currentDeckId(), viewContext.userId())
        );

        if (nextCard == null) {
            displayEndSession();
            return;
        }

        view.displayCard(nextCard);
    }

    private void displayEndSession() {
        view.displaySessionEnded();
        navigator.back();
    }

    private AnkiState toAnkiState(FindStatusResponse findStatusResponse) {
        return new AnkiState(
                viewContext.currentDeckId(),
                findStatusResponse.totalCards(),
                findStatusResponse.redCards(),
                findStatusResponse.orangeCards(),
                findStatusResponse.greenCards(),
                findStatusResponse.lastSession()
        );
    }

    private void loadViewContext() {
        var session = sessionInteractor.currentSession();
        if (session != null) {
            viewContext.login(session.userId(), session.username());
        }

        var deckId = sessionInteractor.deckSelected();
        viewContext.playDeck(deckId);
    }
}
