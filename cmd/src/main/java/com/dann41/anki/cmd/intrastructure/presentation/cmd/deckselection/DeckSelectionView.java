package com.dann41.anki.cmd.intrastructure.presentation.cmd.deckselection;

import com.dann41.anki.cmd.intrastructure.presentation.cmd.CmdMenu;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.CmdMenuItem;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.CmdTools;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.core.BaseView;
import com.dann41.anki.cmd.intrastructure.presentation.cmd.core.Navigator;
import com.dann41.anki.core.deck.application.alldecksfinder.DeckSummary;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DeckSelectionView extends BaseView implements DeckSelectionContract.View {
    private final DeckSelectionContract.Presenter presenter;
    private final CmdTools cmdTools;

    public DeckSelectionView(Navigator navigator, DeckSelectionContract.Presenter presenter, CmdTools cmdTools) {
        super(navigator);
        this.presenter = presenter;
        this.cmdTools = cmdTools;
    }

    @Override
    public void show() {
        configurePresenter(presenter);
        presenter.loadDecks();
    }

    @Override
    public void displayDecks(List<DeckSummary> decks) {
        if (decks.isEmpty()) {
            cmdTools.printInfo("No decks found. Please create one");
            goBack();
        }

        var deckSelector = new CmdMenu(
                "--- My decks ---",
                decks.stream().map(
                        deck -> CmdMenuItem.of(
                                deck.id() + " - " + deck.numberOfQuestions() + " questions. Last played on " +
                                        Optional.ofNullable(deck.lastSession()).map(LocalDate::toString).orElse("never"),
                                deck.id()
                        )
                ).collect(Collectors.toList()),
                "Choose the deck to play: "
        );

        String deckId = cmdTools.printMenu(deckSelector);

        if (deckId.isEmpty()) {
            System.out.println("No deck selected. Back to main menu");
            goBack();
            return;
        }

        presenter.onDeckSelected(deckId);
    }
}
