package com.khakiout.study.ddddemo.domain.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.khakiout.study.ddddemo.domain.validation.constraint.NameConstraint;
import com.khakiout.study.ddddemo.domain.valueobject.EmailValueObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Domain for User.
 */
public class UserEntity extends BaseEntity<Long> {

    @NameConstraint(message = "First name must not be empty.")
    private String firstName;

    @NameConstraint(message = "Last name must not be empty.")
    private String lastName;

    @NotNull
    @Valid
    private EmailValueObject email;

    @Valid
    private List<EmailValueObject> emails;

    public UserEntity(Long id, String firstName, String lastName,
        String email) {
        this(id, firstName, lastName, email, null, null, null);
    }

    /**
     * Instantiate a UserEntity object with the id, name, email and dates pre-populated.
     *
     * @param id the entity id.
     * @param firstName the entity first name.
     * @param lastName the entity last name.
     * @param email the entity email.
     * @param emails other emails.
     * @param createdAt the entity's date of creation.
     * @param updatedAt the entity's date of last modification.
     */
    public UserEntity(Long id, String firstName, String lastName,
        String email, List<String> emails, Date createdAt, Date updatedAt) {
        super(id, createdAt, updatedAt);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = new EmailValueObject(email);
        if (emails != null) {
            this.emails = new ArrayList<>();
            emails.forEach(value -> {
                EmailValueObject emailValueObject = new EmailValueObject(value);
                this.emails.add(emailValueObject);
            });
        }
    }

    /**
     * Instantiate a UserEntity object with the id, name, email and dates pre-populated.
     *
     * @param id the entity id.
     * @param firstName the entity first name.
     * @param lastName the entity last name.
     * @param email the entity email.
     * @param createdAt the entity's date of creation.
     * @param updatedAt the entity's date of last modification.
     */
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

    /**
     * Get the string value of the email.
     *
     * @return the email string.
     */
    @JsonGetter("email")
    public String getEmailValue() {
        String emailValue = null;
        if (email != null) {
            emailValue = email.getEmail();
        }

        return emailValue;
    }

    public List<EmailValueObject> getEmails() {
        return emails;
    }

    /**
     * Set the emails of the user.
     *
     * @param emails the email list.
     */
    @JsonSetter("emails")
    public void setEmails(
        List<String> emails) {
        if (emails != null) {
            this.emails = new ArrayList<>();
            emails.forEach(value -> {
                EmailValueObject emailValueObject = new EmailValueObject(value);
                this.emails.add(emailValueObject);
            });
        }
    }
}
