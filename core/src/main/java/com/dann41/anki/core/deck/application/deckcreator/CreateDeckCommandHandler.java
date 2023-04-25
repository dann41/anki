package com.dann41.anki.core.deck.application.deckcreator;

import com.dann41.anki.shared.application.CommandHandler;

public class CreateDeckCommandHandler implements CommandHandler<CreateDeckCommand> {
    private final DeckCreator deckCreator;

    public CreateDeckCommandHandler(DeckCreator deckCreator) {
        this.deckCreator = deckCreator;
    }

    @Override
    public void handle(CreateDeckCommand command) {
        deckCreator.execute(command);
    }

    @Override
    public Class<CreateDeckCommand> supports() {
        return CreateDeckCommand.class;
    }
}
