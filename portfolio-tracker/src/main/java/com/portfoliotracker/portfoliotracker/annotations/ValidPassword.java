package com.portfoliotracker.portfoliotracker.annotations;

import com.portfoliotracker.portfoliotracker.validators.PasswordMatchesValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({ANNOTATION_TYPE, TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Documented
public @interface ValidPassword {
    String message() default "Password don`t match";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
