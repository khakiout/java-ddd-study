package com.khakiout.study.ddddemo.domain.entity;

import com.baidu.unbiz.fluentvalidator.annotation.FluentValidate;
import com.khakiout.study.ddddemo.domain.exception.EntityValidationException;
import com.khakiout.study.ddddemo.domain.validation.validator.EmailValidator;
import com.khakiout.study.ddddemo.domain.validation.validator.NameValidator;
import com.khakiout.study.ddddemo.domain.valueobject.EmailValueObject;

import java.util.Date;

public class UserEntity extends BaseEntity<Long> {

    @FluentValidate({NameValidator.class})
    private String firstName;

    @FluentValidate({NameValidator.class})
    private String lastName;

    @FluentValidate({EmailValidator.class})
    private EmailValueObject email;

    private Date createdAt;
    private Date updatedAt;

    public UserEntity() {
        super();
    }

    public UserEntity(Long id, String firstName, String lastName,
        String email) {
        this(id, firstName, lastName, email, null, null);
    }

    public UserEntity(Long id, String firstName, String lastName,
        String email, Date createdAt, Date updatedAt) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = new EmailValueObject(email);
        this.createdAt = (createdAt != null) ? createdAt : new Date();
        this.updatedAt = (updatedAt != null) ? updatedAt : new Date();
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
