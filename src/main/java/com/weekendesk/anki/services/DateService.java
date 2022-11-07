package com.weekendesk.anki.services;

public interface DateService {

    /**
     * Return the days since 1/1/1970
     * @return today's epoch day
     */
    long currentEpochDays();

    /**
     * Returns the days between epochDay and today
     * @param epochDay
     * @return days between today and the provided epochDay
     */
    long diffInEpochDays(long epochDay);
}
