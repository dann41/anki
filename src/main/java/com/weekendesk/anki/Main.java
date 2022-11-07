package com.weekendesk.anki;

import com.weekendesk.anki.coordinators.AnkiCoordinator;

public final class Main {

    public static void main(String[] args) {
        AppContext context = new AppContext();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> context.getAppService().saveState()));

        new AnkiCoordinator(context).init();
    }

}
