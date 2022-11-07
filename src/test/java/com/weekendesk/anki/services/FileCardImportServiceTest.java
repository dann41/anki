package com.weekendesk.anki.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FileCardImportServiceTest {

    private FileCardImportService service;

    @Mock
    private CardService cardService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        service = new FileCardImportService(cardService);
        when(cardService.addCard(any())).thenReturn(true);
    }

    @Test
    public void importFromResourceWithUnknownFileReturnsEmptySummary() {
        long result = service.importFromResource("src/test/resources/unknown-file.txt");
        assertThat(result, is(0L));
    }

    @Test
    public void importFromResourceWithWrongLinesFileReturnsEmptySummary() {
        long result = service.importFromResource("src/test/resources/invalid-file.txt");
        assertThat(result, is(0L));
    }

    @Test
    public void importFromResourceWithValidFileSkipHeaderAndInsertCards() {
        long result = service.importFromResource("src/test/resources/valid-file.txt");

        assertThat(result, is(4L));
        verify(cardService, times(4)).addCard(any());
    }

    @Test
    public void importFromResourceShouldReturnEmptySummaryWhenAllInsertCardsFail() {
        when(cardService.addCard(any())).thenReturn(false);

        long result = service.importFromResource("src/test/resources/valid-file.txt");

        assertThat(result, is(0L));
        verify(cardService, times(4)).addCard(any());
    }

    @Test
    public void importFromResourceShouldNotImportIncompleteLines() {
        long result = service.importFromResource("src/test/resources/valid-file-with-incomplete-cards.txt");

        assertThat(result, is(3L));
        verify(cardService, times(3)).addCard(any());
    }
}
