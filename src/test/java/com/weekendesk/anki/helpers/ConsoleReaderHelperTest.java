package com.weekendesk.anki.helpers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class ConsoleReaderHelperTest {

    private ConsoleReaderHelper helper;

    @Mock
    private BufferedReader reader;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.helper = new ConsoleReaderHelper();
    }

    @Test
    public void readLineReturnsWrittenLine() throws IOException {
        when(reader.readLine()).thenReturn("HELLO");

        String result = helper.readLine(reader, "Test message");

        assertThat(result, is("HELLO"));
    }

    @Test
    public void readLineReturnsNullWhenReaderFails() throws IOException {
        when(reader.readLine()).thenThrow(new IOException("test exception"));

        String result = helper.readLine(reader, "Test message");

        assertThat(result, is(nullValue()));
    }

    @Test
    public void readOptionsCallsAsManyTimesAsInvalidInput() throws IOException {
        when(reader.readLine()).thenReturn("a")
                .thenReturn("b")
                .thenReturn("s")
                .thenReturn("e")
                .thenReturn("VALID")
                .thenReturn("other")
                .thenReturn("other")
                .thenReturn("other")
                .thenReturn("other");

        Set<String> validOptions = new HashSet<>();
        validOptions.add("VALID");

        String result = helper.readOption(reader, "INVALID MESSAGE", validOptions);

        assertThat(result, is("VALID"));
        verify(reader, times(5)).readLine();
    }

    @Test
    public void readOptionsReturnsNullWhenReaderFails() throws IOException {
        when(reader.readLine()).thenReturn("a")
                .thenThrow(new IOException("error reading options"));

        Set<String> validOptions = new HashSet<>();
        validOptions.add("VALID");

        String result = helper.readOption(reader, "INVALID MESSAGE", validOptions);

        assertThat(result, is(nullValue()));
        verify(reader, times(2)).readLine();
    }

}
