package com.khakiout.study.ddddemo.interfaces.http.response;

import com.khakiout.study.ddddemo.domain.validation.error.ValidationErrorItem;
import java.util.ArrayList;
import java.util.List;

/**
 * Validation error response. Returned when a validation error occurs.
 * This should contain the list of validation errors.
 */
public class ValidationErrorResponse extends BaseResponse {

    private List<ValidationErrorItem> details;

    public ValidationErrorResponse() {
        super("ValidationError");
        this.details = new ArrayList<>();
    }

    public void addError(ValidationErrorItem errorItem) {
        this.details.add(errorItem);
    }

    public List<ValidationErrorItem> getDetails() {
        return details;
    }
}
