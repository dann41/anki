package com.dann41.anki.shared.application;

public interface QueryHandler<T extends Query<U>, U extends QueryResponse> {
    U handle(T query);

    Class<T> supports();
}
