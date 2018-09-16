package com.khakiout.study.ddddemo.app;

/**
 * Base Application
 */

import com.khakiout.study.ddddemo.domain.entity.BaseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BaseApplication<T extends BaseEntity> {

    Flux<T> getAll();

    Mono<T> findById(String id);

    Mono<T> create(T t);

    Mono<T> update(String id, T t);

    Mono<Void> delete(String id);

    Class<T> getEntityClass();

}
