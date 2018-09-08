package com.khakiout.study.ddddemo.domain.entity;

import static com.baidu.unbiz.fluentvalidator.ResultCollectors.toComplex;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.registry.impl.SimpleRegistry;
import com.khakiout.study.ddddemo.domain.exception.EntityValidationException;
import java.util.Date;

/**
 * Abstract class for entity.
 */
public abstract class BaseEntity<T> {

    protected T id;
    protected Date createdAt;
    protected Date updatedAt;

    protected BaseEntity() {
        this.createdAt = this.updatedAt = new Date();
    }

    protected BaseEntity(T id, Date createdAt, Date updatedAt) {
        this.id = id;
        this.createdAt = (createdAt != null) ? createdAt : new Date();
        this.updatedAt = (updatedAt != null) ? updatedAt : this.createdAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
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
