package com.dann41.anki.core.deck.sessionstarter;

import com.dann41.anki.shared.application.Command;

public record StartSessionCommand(String deckId, String userId) implements Command {
}
