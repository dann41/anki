package com.dann41.anki.core.deck.application.deckremover;

import com.dann41.anki.core.deck.deckremover.DeleteDeckCommand;
import com.dann41.anki.shared.application.CommandHandler;

public class DeleteDeckCommandHandler implements CommandHandler<DeleteDeckCommand> {
    private final DeckDeleter deckDeleter;

    public DeleteDeckCommandHandler(DeckDeleter deckDeleter) {
        this.deckDeleter = deckDeleter;
    }

    @Override
    public void handle(DeleteDeckCommand command) {
        deckDeleter.execute(command);
    }

    @Override
    public Class<DeleteDeckCommand> supports() {
        return DeleteDeckCommand.class;
    }
}
