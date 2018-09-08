package com.khakiout.study.ddddemo.interfaces.http.controller;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * Base for an HTTP controller.
 */
public interface BaseController<T> {

    /**
     * Get the list of the entities.
     *
     * @return the http response with the list of entities.
     */
    Mono<ServerResponse> index(ServerRequest request);

    /**
     * Get entity by its ID.
     *
     * @param id the id
     * @return the http response with the entity representation.
     */
    Mono<ServerResponse> show(ServerRequest request);

    /**
     * Create an entity.
     *
     * @param t the entity to be created.
     * @return the http response.
     */
    Mono<ServerResponse> create(ServerRequest request);

    /**
     * Modify an entity.
     *
     * @param id the id
     * @param t the entity to be updated.
     * @return the http response.
     */
    Mono<ServerResponse> update(ServerRequest request);

    /**
     * Delete the entity.
     *
     * @param id the id
     * @return the http response
     */
    Mono<ServerResponse> delete(ServerRequest request);

}
