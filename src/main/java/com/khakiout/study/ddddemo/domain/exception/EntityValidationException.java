package com.khakiout.study.ddddemo.domain.exception;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.khakiout.study.ddddemo.domain.validator.ValidationErrorObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Throw when a validation error happens inside an entity.
 */
public class EntityValidationException extends Exception {

    private EntityValidationException() {

    }

    private EntityValidationException(String message) {
        super(message);
    }

    private EntityValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    private EntityValidationException(Throwable cause) {
        super(cause);
    }

    private ComplexResult validationResult;

    public EntityValidationException(ComplexResult validationResult) {
        this.validationResult = validationResult;
    }

    @Override
    public String getMessage() {
        return "Entity has validation errors.";
    }

    public List<ValidationErrorObject> getErrorMessages() {
        List<ValidationError> errors = this.validationResult.getErrors();
        List<ValidationErrorObject> errorMessages = new ArrayList<>();

        for (ValidationError error : errors) {
            ValidationErrorObject errorMessage = ValidationErrorObject.create(error.getErrorMsg())
                .setPath(error.getField());
            errorMessages.add(errorMessage);
        }

        return errorMessages;
    }
}
