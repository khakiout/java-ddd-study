package com.khakiout.study.ddddemo.domain.validation.constraint;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ReportAsSingleViolation
@NotNull
@NotBlank
@NotEmpty
@Documented
@Target({FIELD, CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
/**
 * Validation constraint for name properties.
 */
public @interface NameConstraint {

    /**
     * The error message.
     *
     * @return the error message, the default will be returned if not customized.
     */
    String message() default "Name must not be empty.";

    /**
     * The groups where the constraint belongs.
     *
     * @return the constraint groups.
     */
    Class<?>[] groups() default {};

    /**
     * The payload to be validated.
     *
     * @return the payload.
     */
    Class<? extends Payload>[] payload() default {};

}
