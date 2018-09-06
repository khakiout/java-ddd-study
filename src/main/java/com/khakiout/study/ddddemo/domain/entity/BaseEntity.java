package com.khakiout.study.ddddemo.domain.entity;

import static com.baidu.unbiz.fluentvalidator.ResultCollectors.toComplex;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.registry.impl.SimpleRegistry;
import com.khakiout.study.ddddemo.domain.exception.EntityValidationException;

/**
 * Abstract class for entity.
 */
public abstract class BaseEntity<T> {

    protected final T id;

    protected BaseEntity(T id) {
        this.id = id;
    }

    /**
     * Validate the entity based on the rules attached to it.
     *
     * @return the validation result.
     */
    public void validate() throws EntityValidationException {
        ComplexResult result = FluentValidator.checkAll()
            .failOver()
            .configure(new SimpleRegistry())
            .on(this)
            .doValidate()
            .result(toComplex());

        if (!result.isSuccess()) {
            throw new EntityValidationException(result);
        }
    }
}
