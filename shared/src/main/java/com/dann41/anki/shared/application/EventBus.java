package com.dann41.anki.shared.application;

import com.dann41.anki.shared.domain.DomainEvent;

public interface EventBus {
    void publish(DomainEvent event);
}
