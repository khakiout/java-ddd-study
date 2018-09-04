package com.khakiout.study.ddddemo.infrastructure.repositories;

import com.khakiout.study.ddddemo.domain.entity.BaseEntity;

import java.util.List;

/**
 * Base repository.
 */
public interface BaseRepository<T extends BaseEntity> {

    List<T> getAll();

    T findById(String id);

    void create(T t);

    void update(String id, T t);

    void delete(String id);

}
