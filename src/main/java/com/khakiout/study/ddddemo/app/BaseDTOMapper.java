package com.khakiout.study.ddddemo.app;

import com.khakiout.study.ddddemo.domain.entity.BaseEntity;
import com.khakiout.study.ddddemo.domain.exception.EntityValidationException;

/**
 * Base interface for the mapper class.
 *
 * @param <A> the DTO that can be transformed.
 * @param <B> the Entity that can be transformed.
 */
public interface BaseDTOMapper<A extends BaseDTO, B extends BaseEntity> {

    B map(A dto) throws EntityValidationException;

    A map(B entity);
}
