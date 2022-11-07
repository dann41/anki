package com.weekendesk.anki.coordinators;

import com.weekendesk.anki.AppContext;
import com.weekendesk.anki.domain.AppState;
import com.weekendesk.anki.domain.Box;
import com.weekendesk.anki.domain.Card;
import com.weekendesk.anki.helpers.ReaderHelper;
import com.weekendesk.anki.services.AppService;
import com.weekendesk.anki.services.CardImportService;
import com.weekendesk.anki.services.CardService;
import com.weekendesk.anki.services.SessionService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class AnkiCoordinator {

    private static final String BOX_SELECTION_MESSAGE = "\nWhere do you want to put the card? \n" +
            "\tGreen box (" + Box.GREEN.getCmdOption() + ")\n" +
            "\tOrange box (" + Box.ORANGE.getCmdOption() + ")\n" +
            "\tRed box (" + Box.RED.getCmdOption() + ")";

    private static final Set<String> BOX_SELECTION_OPTIONS = new HashSet<>(Arrays.asList(new String[]{
            Box.GREEN.getCmdOption(),
            Box.ORANGE.getCmdOption(),
            Box.RED.getCmdOption(),
    }));

    private final AppService appService;
    private final CardImportService cardImportService;
    private final CardService cardService;
    private final SessionService sessionService;
    private final ReaderHelper readerHelper;

    public AnkiCoordinator(AppContext context) {
        appService = context.getAppService();
        cardImportService = context.getCardImportService();
        cardService = context.getCardService();
        sessionService = context.getSessionService();
        readerHelper = context.getReaderHelper();
    }

    public void init() {
        welcome();
        process();
        farewell();
    }

    private void welcome() {
        System.out.println("Welcome to ANKI");
        appService.loadState();
        printState();
    }

    private void printState() {
        AppState state = appService.getState();
        // Although String.format is slower than concatenation or StringBuilder, it's more readable
        // for simple applications
        System.out.println(String.format("\n[State: G:%d O:%d R:%d - Last played: %d]\n",
                state.cardsInGreen,
                state.cardsInOrange,
                state.cardsInRed,
                state.lastSessionStarted));
    }

    private void process() {
        if (!appService.hasAnyCard()) {
            importQuestionsFile();
        } else if (appService.isTrainingComplete()) {
            System.out.println("Congratulations. Your training has succeed!");
        } else if (cardService.countCards(Box.RED) > 0) {
            startSession();
        }

        if (appService.hasAnyCard() && !appService.isTrainingComplete() && cardService.countCards(Box.RED) == 0) {
            System.out.println("Your daily training is done. Come back tomorrow for more!");
        }
    }

    private void importQuestionsFile() {
        String fileName = readerHelper.readLine("Card filename: ");
        System.out.println("Import cards from file " + fileName);
        long cardsImported = cardImportService.importFromResource(fileName);
        System.out.println(cardsImported + " cards imported");

        // Try to run a session after import
        process();
    }

    private void startSession() {
        boolean hasTrainedToday = appService.hasTrainedToday();
        System.out.println(!hasTrainedToday ? "Start daily session" : "Start review session");

        sessionService.startSession();
        playCards();
        sessionService.finishSession();

        System.out.println(!hasTrainedToday ? "Daily session finished" : "Review session finished");
    }

    private void playCards() {
        Queue<Card> cards = cardService.getCardsSorted(Box.RED);
        Card card;
        System.out.println("\nPending cards for today: " + cards.size());
        while ((card = cards.poll()) != null) {
            playCard(card);
            System.out.println("\nPending cards for today: " + cards.size());
        }
    }

    private void playCard(Card card) {
        System.out.println("Q: " + card.getQuestion());
        readerHelper.readLine("Hit enter when you're ready to see the answer. ");
        System.out.println("A: " + card.getAnswer());

        System.out.println(BOX_SELECTION_MESSAGE);

        String option = readerHelper.readOption("Invalid option. Try again.\n" + BOX_SELECTION_MESSAGE,
                BOX_SELECTION_OPTIONS);
        cardService.moveCardToBox(card, Box.fromOption(option));
    }

    private void farewell() {
        printState();
        System.out.println("Goodbye!");
    }

}
