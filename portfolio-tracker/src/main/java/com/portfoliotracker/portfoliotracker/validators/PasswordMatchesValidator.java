package com.portfoliotracker.portfoliotracker.validators;

import com.portfoliotracker.portfoliotracker.DTO.UserRegistrationDTO;
import com.portfoliotracker.portfoliotracker.annotations.ValidPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordMatchesValidator implements ConstraintValidator<ValidPassword, Object> {

    private Pattern pattern;
    private Matcher matcher;
    private static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*\\d)" +
                                                    "(?=.*[@$!%*?&])" +
                                                    "[A-Za-z\\d@$!%*?&]{8,}$";

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        UserRegistrationDTO user = (UserRegistrationDTO) obj;
        boolean valid = user.getPassword() != null &&
                user.getPassword().equals(user.getMatchingPassword()) &&
                isValidPassword(user.getPassword());

        if (!valid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Passwords must match and be strong")
                    .addPropertyNode("matchingPassword") // прив'язуємо до поля
                    .addConstraintViolation();
        }

        return valid;
    }

    private boolean isValidPassword(String password) {
        this.pattern = Pattern.compile(PASSWORD_PATTERN);
        this.matcher = this.pattern.matcher(password);
        return this.matcher.matches();
    }
}
