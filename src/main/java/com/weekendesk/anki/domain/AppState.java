package com.weekendesk.anki.domain;

public class AppState {

    // Made public for simplicity
    public final long cardsInGreen;
    public final long cardsInOrange;
    public final long cardsInRed;
    public final long lastSessionStarted;

    public AppState(long cardsInGreen, long cardsInOrange, long cardsInRed, long lastSessionStarted) {
        this.cardsInGreen = cardsInGreen;
        this.cardsInOrange = cardsInOrange;
        this.cardsInRed = cardsInRed;
        this.lastSessionStarted = lastSessionStarted;
    }

}
