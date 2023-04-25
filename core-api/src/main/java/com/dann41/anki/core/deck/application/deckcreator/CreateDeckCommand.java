package com.dann41.anki.core.deck.application.deckcreator;

import com.dann41.anki.shared.application.Command;

public record CreateDeckCommand(String deckId, String userId, String collectionId) implements Command {
}
