package com.khakiout.study.ddddemo.domain.validator;

public class ValidationErrorObject {

    private String path;
    private String message;

    private ValidationErrorObject() {

    }

    public static ValidationErrorObject create(String errorMsg) {
        return new ValidationErrorObject().setErrorMessage(errorMsg);
    }

    public ValidationErrorObject setErrorMessage(String message) {
        this.message = message;
        return this;
    }

    public ValidationErrorObject setPath(String path) {
        this.path = path;
        return this;
    }

    public String getPath() {
        return path;
    }

    public String getMessage() {
        return message;
    }
}
