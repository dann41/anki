package com.dann41.anki.core.deck.application.cardsolver;

import com.dann41.anki.core.deck.cardsolver.SolveCardCommand;
import com.dann41.anki.shared.application.CommandHandler;

public class SolveCardCommandHandler implements CommandHandler<SolveCardCommand> {

    private final CardSolver cardSolver;

    public SolveCardCommandHandler(CardSolver cardSolver) {
        this.cardSolver = cardSolver;
    }

    @Override
    public void handle(SolveCardCommand command) {
        cardSolver.execute(command);
    }

    @Override
    public Class<SolveCardCommand> supports() {
        return SolveCardCommand.class;
    }
}
