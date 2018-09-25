package com.khakiout.study.ddddemo.interfaces.http.response;

import com.khakiout.study.ddddemo.domain.validation.error.ValidationErrorItem;
import java.util.ArrayList;
import java.util.List;

public class ValidationReport {

    private final String type = "ValidationError";
    private List<ValidationErrorItem> details;

    public ValidationReport() {
        this.details = new ArrayList<>();
    }

    public void addError(ValidationErrorItem errorItem) {
        this.details.add(errorItem);
    }

    public String getType() {
        return type;
    }

    public List<ValidationErrorItem> getDetails() {
        return details;
    }
}
