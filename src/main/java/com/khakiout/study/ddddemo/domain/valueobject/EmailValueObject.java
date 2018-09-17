package com.khakiout.study.ddddemo.domain.valueobject;

public class EmailValueObject {

    private final String email;

    public EmailValueObject(final String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
