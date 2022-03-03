package com.volvo.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;

/**
 * Custom checker for Vehicle type
 * <br>
 * <br>
 *
 * @author Dinesh Kumar Busireddy
 * @since 03-02-2022
 */
@Documented
@Constraint(validatedBy = VehicleTypeValidator.class)
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface VehicleTypeChecker {
    String message() default "Invalid Vehicle Type";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
