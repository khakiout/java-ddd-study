package com.khakiout.study.ddddemo.domain.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.khakiout.study.ddddemo.domain.valueobject.EmailValueObject;

import java.util.Date;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * Domain for User.
 */
public class UserEntity extends BaseEntity<Long> {

    @NotBlank(message = "First name must not be blank.")
    @NotEmpty(message = "First name must not be empty.")
    private String firstName;

    @NotBlank(message = "Last name must not be blank.")
    @NotEmpty(message = "Last name must not be empty.")
    private String lastName;

    @Valid
    private EmailValueObject email;

    public UserEntity() {
        super();
    }

    public UserEntity(Long id, String firstName, String lastName,
        String email) {
        this(id, firstName, lastName, email, null, null);
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
        if (email != null) {
            return email.getEmail();
        }

        return null;
    }

}
