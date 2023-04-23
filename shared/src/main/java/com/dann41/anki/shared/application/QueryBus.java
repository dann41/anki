package com.dann41.anki.shared.application;

public interface QueryBus {
    <T extends Query<U>, U extends QueryResponse> U publish(T query);
}
