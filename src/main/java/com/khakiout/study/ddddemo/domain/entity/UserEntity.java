package com.khakiout.study.ddddemo.domain.entity;

import com.baidu.unbiz.fluentvalidator.annotation.FluentValidate;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
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

    public UserEntity() {
        super();
    }

    public UserEntity(Long id, String firstName, String lastName,
        String email) {
        this(id, firstName, lastName, email, null, null);
    }

    public UserEntity(Long id, String firstName, String lastName,
        String email, Date createdAt, Date updatedAt) {
        super(id, createdAt, updatedAt);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = new EmailValueObject(email);
    }

    @JsonSetter("email")
    public void setEmail(String email) {
        this.email = new EmailValueObject(email);
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

    @JsonIgnore
    public EmailValueObject getEmail() {
        return email;
    }

    @JsonGetter("email")
    public String getEmailValue() {
        if (email != null) {
            return email.getEmail();
        }

        return null;
    }

}
