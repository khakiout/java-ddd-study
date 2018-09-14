package com.khakiout.study.ddddemo.interfaces.http.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * Base for a Route handler for specific Entity.
 *
 * @see com.khakiout.study.ddddemo.domain.entity.BaseEntity
 */
public interface BaseHandler {

    /**
     * Get the list of the entities.
     *
     * @param request the server request
     * @return the http response with the list of Entities.
     */
    Mono<ServerResponse> index(ServerRequest request);

    /**
     * Get entity by its ID.
     *
     * @param request the server request with the Entity ID as request param.
     * @return the http response with the entity representation.
     */
    Mono<ServerResponse> show(ServerRequest request);

    /**
     * Create an entity.
     *
     * @param request the server request with the Entity details to be created.
     * @return the http response.
     */
    Mono<ServerResponse> create(ServerRequest request);

    /**
     * Modify an entity.
     *
     * @param request the server request with the Entity details to be updated.
     * @return the http response.
     */
    Mono<ServerResponse> update(ServerRequest request);

    /**
     * Delete an entity.
     *
     * @param request the server request with Entity ID as route entry.
     * @return the http response
     */
    Mono<ServerResponse> delete(ServerRequest request);

}
