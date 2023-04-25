package com.dann41.anki.core.deck.application.deckremover;

import com.dann41.anki.core.deck.domain.Deck;
import com.dann41.anki.core.deck.domain.DeckFinder;
import com.dann41.anki.core.deck.domain.DeckId;
import com.dann41.anki.core.deck.domain.DeckRepository;
import com.dann41.anki.core.user.domain.UserId;

public class DeckDeleter {
    private final DeckFinder deckFinder;
    private final DeckRepository deckRepository;

    public DeckDeleter(DeckRepository deckRepository) {
        this.deckRepository = deckRepository;
        this.deckFinder = new DeckFinder(deckRepository);
    }

    public void execute(DeleteDeckCommand command) {
        Deck deck = deckFinder.execute(new DeckId(command.deckId()), new UserId(command.userId()));

        deck.delete();

        deckRepository.save(deck);
    }
}
