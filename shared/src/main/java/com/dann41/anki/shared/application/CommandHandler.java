package com.dann41.anki.shared.application;

public interface CommandHandler<T extends Command> {
    void handle(T command);

    Class<T> supports();
}
