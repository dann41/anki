package com.dann41.anki.cmd.infrastructure.presentation.cmd.deckplayer;

import com.dann41.anki.cmd.infrastructure.presentation.cmd.AnkiState;
import com.dann41.anki.cmd.infrastructure.presentation.cmd.core.Core;
import com.dann41.anki.core.deck.application.cardpicker.CardPickerResponse;

public interface DeckPlayerContract {
    interface View extends Core.View {
        void displayState(AnkiState ankiState);

        void displayCard(CardPickerResponse cardPickerResponse);

        void requestBoxCategorization(String cardId);

        void displaySessionEnded();

        void displayWrongBoxName(String cardId, String box);

        void displayDeckNotFound(String deckId);
    }

    interface Presenter extends Core.Presenter<View> {
        void playDeck();

        void solveCard(String cardId, String box);
    }
}
