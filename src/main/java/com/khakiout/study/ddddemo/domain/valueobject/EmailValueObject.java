package com.khakiout.study.ddddemo.domain.valueobject;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class EmailValueObject {

    @NotNull(message = "Email must be valid.")
    @Email(regexp = ".+@.+\\..+", message = "Email must be valid.")
    private final String email;

    public EmailValueObject(final String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
