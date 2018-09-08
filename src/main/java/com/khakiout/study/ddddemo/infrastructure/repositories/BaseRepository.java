package com.khakiout.study.ddddemo.infrastructure.repositories;

import com.khakiout.study.ddddemo.domain.entity.BaseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * Base repository.
 */
public interface BaseRepository<T extends BaseEntity> {

    Flux<T> getAll();

    Mono<T> findById(String id);

    Mono<T> create(T t);

    Mono<T> update(String id, T t);

    Mono<Void> delete(String id);

}
