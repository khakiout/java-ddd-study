package com.khakiout.study.ddddemo.infrastructure.mapper;

import com.khakiout.study.ddddemo.domain.entity.BaseEntity;
import com.khakiout.study.ddddemo.domain.exception.EntityValidationException;
import com.khakiout.study.ddddemo.infrastructure.models.BaseRepositoryModel;

public interface BaseRepositoryModelMapper<A extends BaseRepositoryModel, B extends BaseEntity> {

    B map(A repositoryModel) throws EntityValidationException;

    A map(B entity);

}
