package com.khakiout.study.ddddemo.domain.entity;

import com.khakiout.study.ddddemo.domain.BaseModel;
import com.khakiout.study.ddddemo.domain.valueobject.EmailValueObject;
import java.util.Date;

public class UserEntity extends BaseModel {

    private final Long id;
    private final String firstName;
    private final String lastName;
    private final EmailValueObject email;
    private final Date createdAt;
    private final Date updatedAt;

    public UserEntity(Long id, String firstName, String lastName,
        String email, Date createdAt, Date updatedAt) {
        this.id = id;
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
