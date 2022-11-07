package com.weekendesk.anki.services;

import com.weekendesk.anki.repositories.SessionRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SessionServiceImplTest {

    private SessionServiceImpl sessionService;

    @Mock
    private DateService dateService;

    @Mock
    private SessionRepository sessionRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.sessionService = new SessionServiceImpl(dateService, sessionRepository);
    }

    @Test
    public void startSessionUpdatesDayOfLastSession() {
        when(dateService.currentEpochDays()).thenReturn(123456L);
        sessionService.startSession();
        verify(sessionRepository, times(1)).setLastSessionStarted(123456L);
    }
}
