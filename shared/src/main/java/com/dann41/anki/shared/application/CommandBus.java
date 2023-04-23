package com.dann41.anki.shared.application;

public interface CommandBus {
    <T extends Command> void publish(T command);
}
