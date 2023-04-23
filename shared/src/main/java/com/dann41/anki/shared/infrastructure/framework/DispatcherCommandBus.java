package com.dann41.anki.shared.infrastructure.framework;

import com.dann41.anki.shared.application.Command;
import com.dann41.anki.shared.application.CommandBus;
import com.dann41.anki.shared.application.CommandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class DispatcherCommandBus implements CommandBus {

    private static final Logger LOGGER = LoggerFactory.getLogger(DispatcherCommandBus.class);
    private final Map<Class<? extends Command>, CommandHandler<?>> commandHandlers;

    public DispatcherCommandBus(Map<Class<? extends Command>, CommandHandler<?>> commandHandlers) {
        this.commandHandlers = commandHandlers;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Command> void publish(T command) {
        var handler = (CommandHandler<T>) commandHandlers.get(command.getClass());
        if (handler == null) {
            LOGGER.info("Command not supported: " + command.getClass().getSimpleName());
        } else {
            handler.handle(command);
        }
    }
}
