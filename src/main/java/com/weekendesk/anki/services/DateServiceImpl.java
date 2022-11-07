package com.weekendesk.anki.services;

import java.time.LocalDate;

public class DateServiceImpl implements DateService {

    @Override
    public long currentEpochDays() {
        return LocalDate.now().toEpochDay();
    }

    @Override
    public long diffInEpochDays(long epochDay) {
        return currentEpochDays() - epochDay;
    }

}
