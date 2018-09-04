package com.khakiout.study.ddddemo.domain.entity;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.annotation.FluentValidate;
import com.khakiout.study.ddddemo.domain.exception.EntityValidationException;
import com.khakiout.study.ddddemo.domain.validation.validator.EmailValidator;
import com.khakiout.study.ddddemo.domain.validation.validator.NameValidator;
import com.khakiout.study.ddddemo.domain.valueobject.EmailValueObject;

import java.util.Date;

public class UserEntity extends BaseEntity<Long> {

    @FluentValidate({NameValidator.class})
    private final String firstName;

    @FluentValidate({NameValidator.class})
    private final String lastName;

    @FluentValidate({EmailValidator.class})
    private final EmailValueObject email;

    private final Date createdAt;
    private final Date updatedAt;

    public UserEntity(Long id, String firstName, String lastName,
        String email) throws EntityValidationException {
        this(id, firstName, lastName, email, null, null);
    }

    public UserEntity(Long id, String firstName, String lastName,
        String email, Date createdAt, Date updatedAt) throws EntityValidationException {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = new EmailValueObject(email);
        this.createdAt = (createdAt != null) ? createdAt : new Date();
        this.updatedAt = (updatedAt != null) ? updatedAt : new Date();

        ComplexResult validationResult = this.validate();
        if (!validationResult.isSuccess()) {
            throw new EntityValidationException(validationResult);
        }
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public EmailValueObject getEmail() {
        return email;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

}
