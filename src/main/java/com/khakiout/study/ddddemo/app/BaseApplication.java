package com.khakiout.study.ddddemo.app;

/**
 * Base Application
 */

import com.khakiout.study.ddddemo.domain.exception.EntityValidationException;
import java.util.List;

public interface BaseApplication<T extends BaseDTO> {

    List<T> getAll();

    T findById(String id);

    void create(T t) throws EntityValidationException;

    void update(String id, T t);

    void delete(String id);

}
