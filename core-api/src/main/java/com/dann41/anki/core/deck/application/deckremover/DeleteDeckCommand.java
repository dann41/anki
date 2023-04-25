package com.dann41.anki.core.deck.application.deckremover;

import com.dann41.anki.shared.application.Command;

public record DeleteDeckCommand(String deckId, String userId) implements Command {
}
