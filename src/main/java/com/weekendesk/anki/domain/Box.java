package com.weekendesk.anki.domain;

import java.util.Arrays;

public enum Box {

    RED("R"),
    ORANGE("O"),
    GREEN("G");

    private final String cmdOption;

    Box(String cmdOption) {
        this.cmdOption = cmdOption;
    }

    public String getCmdOption() {
        return cmdOption;
    }


    public static Box fromOption(String option) {
        return Arrays.stream(Box.values())
            .filter(d -> d.getCmdOption().equals(option))
            .findFirst()
            .orElse(Box.RED);
    }
}
