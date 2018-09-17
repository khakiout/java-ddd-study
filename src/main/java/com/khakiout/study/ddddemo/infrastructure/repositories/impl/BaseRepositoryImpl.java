package com.khakiout.study.ddddemo.infrastructure.repositories.impl;

import com.khakiout.study.ddddemo.infrastructure.models.BaseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

public abstract class BaseRepositoryImpl<M extends BaseModel> {

    Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @Autowired
    protected final SpringValidatorAdapter validatorAdapter;

    protected BaseRepositoryImpl(SpringValidatorAdapter validatorAdapter) {
        this.validatorAdapter = validatorAdapter;
    }

    void validate(M model) {
        DataBinder dataBinder = new DataBinder(model);
        dataBinder.addValidators(validatorAdapter);
        dataBinder.validate();
        BindingResult result = dataBinder.getBindingResult();
        if (result.hasErrors()) {
            logger.warn("Has {} validation errors", result.getAllErrors().size());
            throw new DataIntegrityViolationException("Has validation errors");
        }
    }
}
