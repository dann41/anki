package com.dann41.anki.shared.infrastructure.framework;

import com.dann41.anki.shared.application.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class DispatcherQueryBus implements QueryBus {

    private static final Logger LOGGER = LoggerFactory.getLogger(DispatcherQueryBus.class);
    private final Map<Class<? extends Query<? extends QueryResponse>>, QueryHandler<?, ?>> queryHandlers;

    public DispatcherQueryBus(Map<Class<? extends Query<?>>, QueryHandler<?, ?>> queryHandlers) {
        this.queryHandlers = queryHandlers;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Query<U>, U extends QueryResponse> U publish(T query) {
        var handler = (QueryHandler<T, U>) queryHandlers.get(query.getClass());
        if (handler == null) {
            LOGGER.info("Query not supported: " + query.getClass().getSimpleName());
            return null;
        } else {
            return handler.handle(query);
        }
    }
}
