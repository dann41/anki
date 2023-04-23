package com.dann41.anki.shared.application;

import com.dann41.anki.shared.domain.DomainEvent;

public interface EventHandler<T extends DomainEvent> {
    void on(T event);
}
