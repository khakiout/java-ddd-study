package com.khakiout.study.ddddemo.domain.validation.validator;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.Validator;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import com.khakiout.study.ddddemo.domain.valueobject.EmailValueObject;

public class EmailValidator extends ValidatorHandler<EmailValueObject> implements
    Validator<EmailValueObject> {

  @Override
  public boolean validate(ValidatorContext context, EmailValueObject emailValueObject) {
    if (emailValueObject == null) {
      ValidationError error = new ValidationError();
      error.setErrorMsg("Email must have a value.");
      error.setField("email");
      context.addError(error);
      return false;
    }

    org.apache.commons.validator.routines.EmailValidator emailValidator =
        org.apache.commons.validator.routines.EmailValidator.getInstance();

    String email = emailValueObject.getEmail();
    if (!emailValidator.isValid(email)) {
      ValidationError error = new ValidationError();
      error.setErrorMsg("Email must be a valid email address.");
      error.setField("email");
      context.addError(error);
      return false;
    }

    return true;
  }

}
