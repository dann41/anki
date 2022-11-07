package com.weekendesk.anki.services;

import com.weekendesk.anki.domain.Box;
import com.weekendesk.anki.repositories.AppRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class AppServiceImplTest {

    private AppServiceImpl service;

    @Mock
    private CardService cardService;

    @Mock
    private SessionService sessionService;

    @Mock
    private DateService dateService;

    @Mock
    private AppRepository appRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        service = new AppServiceImpl(cardService, sessionService, dateService, appRepository);
    }

    @Test
    public void loadStateRotatesBoxAtMostTwoAfterSevenDaysWithoutTraining() {
        when(dateService.diffInEpochDays(anyLong())).thenReturn(7L);

        service.loadState();

        verify(appRepository, times(1)).load();
        verify(cardService, times(2)).rotateCards();
    }

    @Test
    public void hastTrainedTodayReturnsTrueWhenDiffIs0() {
        when(dateService.diffInEpochDays(anyLong())).thenReturn(0L);
        boolean result = service.hasTrainedToday();
        assertThat(result, is(true));
    }

    @Test
    public void hastTrainedTodayReturnsTrueWhenDiffIsNot0() {
        when(dateService.diffInEpochDays(anyLong())).thenReturn(4L);
        boolean result = service.hasTrainedToday();
        assertThat(result, is(false));
    }

    @Test
    public void isTrainingCompleteReturnsFalseWhenThereIsACardNotFullLearnt() {
        when(cardService.countCards(Box.GREEN)).thenReturn(4L);
        when(cardService.countCards(Box.RED)).thenReturn(0L);
        when(cardService.countCards(Box.ORANGE)).thenReturn(1L);

        boolean result = service.isTrainingComplete();
        assertThat(result, is(false));
    }

    @Test
    public void isTrainingCompleteReturnsFalseWhenThereIsNoCard() {
        when(cardService.countCards(Box.GREEN)).thenReturn(0L);
        when(cardService.countCards(Box.RED)).thenReturn(0L);
        when(cardService.countCards(Box.ORANGE)).thenReturn(0L);

        boolean result = service.isTrainingComplete();
        assertThat(result, is(false));
    }

    @Test
    public void hasAnyCardReturnsTrueWhenABoxIsNotEmpty() {
        when(cardService.countCards(Box.GREEN)).thenReturn(1L);
        when(cardService.countCards(Box.RED)).thenReturn(0L);
        when(cardService.countCards(Box.ORANGE)).thenReturn(0L);

        boolean result = service.hasAnyCard();
        assertThat(result, is(true));
    }

    @Test
    public void hasAnyCardReturnsTrueWhenAllBoxesAreEmpty() {
        when(cardService.countCards(Box.GREEN)).thenReturn(0L);
        when(cardService.countCards(Box.RED)).thenReturn(0L);
        when(cardService.countCards(Box.ORANGE)).thenReturn(0L);

        boolean result = service.hasAnyCard();
        assertThat(result, is(false));
    }
}
