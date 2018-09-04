package com.khakiout.study.ddddemo.interfaces.http.controller;

import com.khakiout.study.ddddemo.app.BaseDTO;

import java.util.List;

import org.springframework.http.ResponseEntity;

/**
 * Base for an HTTP controller.
 */
public interface BaseController<T extends BaseDTO> {

    /**
     * Get entity by its ID.
     *
     * @param id the id
     * @return the http response with the entity representation.
     */
    ResponseEntity<T> get(String id);

    /**
     * Get the list of the entities.
     *
     * @return the http response with the list of entities.
     */
    ResponseEntity<List<T>> get();

    /**
     * Create an entity.
     *
     * @param t the entity to be created.
     * @return the http response.
     */
    ResponseEntity create(T t);

    /**
     * Modify an entity.
     *
     * @param id the id
     * @param t the entity to be updated.
     * @return the http response.
     */
    ResponseEntity update(String id, T t);

    /**
     * Delete the entity.
     *
     * @param id the id
     * @return the http response
     */
    ResponseEntity delete(String id);

}
