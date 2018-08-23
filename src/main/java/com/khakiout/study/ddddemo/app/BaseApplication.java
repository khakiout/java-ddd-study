package com.khakiout.study.ddddemo.app;

/**
 * Base Application
 */

import java.util.List;

public interface BaseApplication<T extends BaseDTO> {

    List<T> getAll();

    T findById(String id);

    void create(T t);

    void update(String id, T t);

    void delete(String id);

}
