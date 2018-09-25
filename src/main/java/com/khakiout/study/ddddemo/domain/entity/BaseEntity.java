package com.khakiout.study.ddddemo.domain.entity;

import com.khakiout.study.ddddemo.domain.validation.exception.EntityValidationException;
import java.util.Date;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

/**
 * Abstract class for entity.
 */
public abstract class BaseEntity<T> {

    protected T id;
    private Date createdAt;
    private Date updatedAt;

    BaseEntity() {
        this.createdAt = this.updatedAt = new Date();
    }

    BaseEntity(T id, Date createdAt, Date updatedAt) {
        this.id = id;
        this.createdAt = (createdAt != null) ? new Date(createdAt.getTime()) : new Date();
        this.updatedAt = (updatedAt != null) ? new Date(updatedAt.getTime()) : this.createdAt;
    }

    public T getId() {
        return id;
    }

    public Date getCreatedAt() {
        return new Date(createdAt.getTime());
    }

    public Date getUpdatedAt() {
        return new Date(updatedAt.getTime());
    }

    /**
     * Validate the entity based on the rules attached to it.
     */
    public void validate() throws EntityValidationException {
        DataBinder binder = new DataBinder(this);
        binder.addValidators(this.getAdapter());

        // validate the target object
        binder.validate();

        // get BindingResult that includes any validation errors
        BindingResult results = binder.getBindingResult();

        if (results.hasErrors()) {
            throw new EntityValidationException(results.getAllErrors());
        }
    }

    private SpringValidatorAdapter getAdapter() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        SpringValidatorAdapter validatorAdapter = new SpringValidatorAdapter(validator);

        return validatorAdapter;
    }

}
