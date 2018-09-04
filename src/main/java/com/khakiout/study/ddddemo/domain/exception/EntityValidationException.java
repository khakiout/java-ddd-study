package com.khakiout.study.ddddemo.domain.exception;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.khakiout.study.ddddemo.domain.validation.response.ValidationErrorItem;

import com.khakiout.study.ddddemo.domain.validation.response.ValidationReport;
import java.util.ArrayList;
import java.util.List;

/**
 * Thrown when a validation error happens inside an entity.
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

    public ValidationReport getErrorMessages() {
        List<ValidationError> errors = this.validationResult.getErrors();
        ValidationReport validationReport = new ValidationReport();

        for (ValidationError error : errors) {
            ValidationErrorItem errorMessage = ValidationErrorItem.create(error.getErrorMsg())
                .setPath(error.getField());
            validationReport.addError(errorMessage);
        }

        return validationReport;
    }
}
