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
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

public class AnkiCoordinatorTest {

    private AnkiCoordinator ankiCoordinator;

    @Mock
    private AppContext context;

    @Mock
    private AppService appService;

    @Mock
    private CardImportService cardImportService;

    @Mock
    private CardService cardService;

    @Mock
    private SessionService sessionService;

    @Mock
    private ReaderHelper readerHelper;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        when(context.getAppService()).thenReturn(appService);
        when(context.getCardImportService()).thenReturn(cardImportService);
        when(context.getCardService()).thenReturn(cardService);
        when(context.getSessionService()).thenReturn(sessionService);
        when(context.getReaderHelper()).thenReturn(readerHelper);

        ankiCoordinator = new AnkiCoordinator(context);

        when(appService.getState()).thenReturn(new AppState(0, 0, 0, 0));
    }

    @Test
    public void initForFirstTimeWillLaunchImportAction() {
        when(appService.hasAnyCard()).thenReturn(false)
                .thenReturn(true);
        when(readerHelper.readLine(any())).thenReturn("a file");

        ankiCoordinator.init();

        verify(cardImportService, times(1)).importFromResource(eq("a file"));
    }

    @Test
    public void initWithCompleteGameDoesNotStartSession() {
        when(appService.hasAnyCard()).thenReturn(true);
        when(appService.isTrainingComplete()).thenReturn(true);

        ankiCoordinator.init();

        verify(sessionService, never()).startSession();
    }

    @Test
    public void initForANewDayWithNoRedCardsDoesNotStartSession() {
        when(appService.hasAnyCard()).thenReturn(true);
        when(appService.isTrainingComplete()).thenReturn(false);
        when(appService.hasTrainedToday()).thenReturn(false);
        when(cardService.countCards(Box.RED)).thenReturn(0L);
        when(appService.getState()).thenReturn(new AppState(0, 2, 0, 0));

        ankiCoordinator.init();

        verify(sessionService, never()).startSession();
    }

    @Test
    public void initForANewDayWithARedCardsDoesStartSessionAndPlayCards() {
        when(appService.getState()).thenReturn(new AppState(0, 2, 0, 0));

        when(appService.hasAnyCard()).thenReturn(true);
        when(appService.isTrainingComplete()).thenReturn(false);
        when(appService.hasTrainedToday()).thenReturn(false);
        when(cardService.countCards(Box.RED)).thenReturn(1L);
        when(cardService.getCardsSorted(Box.RED)).thenReturn(dummyQueue());

        when(readerHelper.readOption(any(), any())).thenReturn("R")
                .thenReturn("G")
                .thenReturn("O")
                .thenReturn("R");

        ankiCoordinator.init();

        verify(sessionService, times(1)).startSession();
        verify(cardService, times(1)).getCardsSorted(Box.RED);
        verify(cardService, times(1)).moveCardToBox(any(), eq(Box.GREEN));
        verify(cardService, times(1)).moveCardToBox(any(), eq(Box.ORANGE));
        verify(cardService, times(2)).moveCardToBox(any(), eq(Box.RED));
        verify(sessionService, times(1)).finishSession();
    }

    private Queue<Card> dummyQueue() {
        return Arrays.asList(
                getCard("A?", "A!", Box.RED),
                getCard("B?", "B!", Box.RED),
                getCard("C?", "C!", Box.RED),
                getCard("D?", "D!", Box.RED)
        ).stream().collect(Collectors.toCollection(LinkedList::new));
    }

    private Card getCard(String question, String answer, Box box) {
        Card card = new Card();
        card.setQuestion(question);
        card.setAnswer(answer);
        card.setBox(box);
        return card;
    }
}
