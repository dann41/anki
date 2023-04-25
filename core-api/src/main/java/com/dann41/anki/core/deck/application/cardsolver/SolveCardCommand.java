package com.dann41.anki.core.deck.application.cardsolver;

import com.dann41.anki.shared.application.Command;

public record SolveCardCommand(String deckId, String userId, String cardId, String boxName) implements Command {
}
