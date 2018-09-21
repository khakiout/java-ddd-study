package com.khakiout.study.ddddemo.domain.exception;

import com.khakiout.study.ddddemo.domain.validation.response.ValidationErrorItem;

import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

/**
 * Thrown when a validation error happens inside an entity.
 */
public class EntityValidationException extends Exception {

    private final List<ObjectError> validationErrors;

    private EntityValidationException() {
        this.validationErrors = new ArrayList<>();
    }

    private EntityValidationException(String message) {
        super(message);
        this.validationErrors = new ArrayList<>();
    }

    private EntityValidationException(String message, Throwable cause) {
        super(message, cause);
        this.validationErrors = new ArrayList<>();
    }

    private EntityValidationException(Throwable cause) {
        super(cause);
        this.validationErrors = new ArrayList<>();
    }

    public EntityValidationException(List<ObjectError> validationErrors) {
        this.validationErrors = validationErrors;
    }

    @Override
    public String getMessage() {
        return "Entity has validation errors.";
    }

    /**
     * Return the error messages obtain from the validation process of the entity.
     *
     * @return the validation report.
     */
    public List<ValidationErrorItem> getErrorMessages() {
        List<ValidationErrorItem> errorMessages = new ArrayList<>();
        for (ObjectError error : validationErrors) {
            FieldError fieldError = (FieldError) error;
            ValidationErrorItem errorMessage = ValidationErrorItem
                .create(fieldError.getDefaultMessage())
                .setPath(fieldError.getField());
            errorMessages.add(errorMessage);
        }

        return errorMessages;
    }
}
