package com.khakiout.study.ddddemo.infrastructure.models;

import javax.persistence.Embeddable;

@Embeddable
public class Email {

    @javax.validation.constraints.Email
    private String email;

    protected Email() { }

    public Email(final String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}