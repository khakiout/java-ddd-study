package com.khakiout.study.ddddemo.domain.entity;

import static com.baidu.unbiz.fluentvalidator.ResultCollectors.toComplex;
import static com.baidu.unbiz.fluentvalidator.ResultCollectors.toSimple;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.annotation.FluentValidate;
import com.baidu.unbiz.fluentvalidator.registry.impl.SimpleRegistry;
import com.khakiout.study.ddddemo.domain.exception.EntityValidationException;
import com.khakiout.study.ddddemo.domain.validator.EmailValidator;
import com.khakiout.study.ddddemo.domain.validator.NameValidator;
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

    private ComplexResult validate() {
        ComplexResult result = FluentValidator.checkAll()
            .failOver()
            .configure(new SimpleRegistry())
            .on(this)
            .doValidate()
            .result(toComplex());

        return result;
    }
}
