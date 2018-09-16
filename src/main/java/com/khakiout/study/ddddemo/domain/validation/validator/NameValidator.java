package com.khakiout.study.ddddemo.domain.validation.validator;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.Validator;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import org.springframework.util.StringUtils;

public class NameValidator extends ValidatorHandler<String> implements Validator<String> {

  @Override
  public boolean validate(ValidatorContext context, String name) {
    if (StringUtils.isEmpty(name)) {
      ValidationError error = new ValidationError();
      error.setErrorMsg("Name must have a value.");
      error.setField("name");
      context.addError(error);

      return false;
    }

    return true;
  }

}
