package com.khakiout.study.ddddemo.infrastructure;

import com.khakiout.study.ddddemo.domain.BaseModel;
import java.util.List;

/**
 * Base repository.
 */
public interface BaseRepository<T extends BaseModel> {

    List<T> getAll();

    T findById(String id);

    void create(T t);

    void update(String id, T t);

    void delete(String id);

}
