package com.khakiout.study.ddddemo.app;

/**
 * Base Application
 */

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BaseApplication<T> {

    Flux<T> getAll();

    Mono<T> findById(String id);

    Mono<T> create(T t);

    Mono<T> update(String id, T t);

    Mono<Void> delete(String id);

}
