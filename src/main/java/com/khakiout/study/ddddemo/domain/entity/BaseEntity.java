package com.khakiout.study.ddddemo.domain.entity;

/**
 * Abstract class for entity.
 */
public abstract class BaseEntity<T> {

    protected final T id;

    protected BaseEntity(T id) {
        this.id = id;
    }
}
