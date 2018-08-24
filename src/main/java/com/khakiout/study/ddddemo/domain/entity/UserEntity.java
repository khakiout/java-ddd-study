package com.khakiout.study.ddddemo.domain.entity;

import com.khakiout.study.ddddemo.domain.BaseModel;
import com.khakiout.study.ddddemo.domain.valueobject.EmailValueObject;
import java.util.Date;

public class UserEntity extends BaseModel {

    private Long id;
    private String firstName;
    private String lastName;
    private EmailValueObject email;
    private Date createdAt;
    private Date updatedAt;

    public UserEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email.getEmail();
    }

    public void setEmail(EmailValueObject email) {
        this.email = email;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
