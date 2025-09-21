package com.portfoliotracker.portfoliotracker.validators;

import com.portfoliotracker.portfoliotracker.DTO.UserRegistrationDTO;
import com.portfoliotracker.portfoliotracker.annotations.ValidPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PasswordMatchesValidator implements ConstraintValidator<ValidPassword, Object> {

    private Pattern pattern;
    private Matcher matcher;
    private static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*\\d)" +
                                                    "(?=.*[@$!%*?&])" +
                                                    "[A-Za-z\\d@$!%*?&]{8,}$";
    private final MessageSource messageSource;

    @Autowired
    public PasswordMatchesValidator(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        UserRegistrationDTO user = (UserRegistrationDTO) obj;

        boolean validPassword = user.getPassword() != null &&
                isValidPassword(user.getPassword());

        assert user.getPassword() != null;

        boolean isPasswordMatch = isPasswordMatch(user.getPassword(), user.getMatchingPassword());

        if (!validPassword) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(messageSource.getMessage("registration.passwordError", null, LocaleContextHolder.getLocale()))
                    .addPropertyNode("password") // прив'язуємо до поля
                    .addConstraintViolation();
        } else if (!isPasswordMatch) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(messageSource.getMessage("registration.matchingPasswordError", null, LocaleContextHolder.getLocale()))
                    .addPropertyNode("matchingPassword")
                    .addConstraintViolation();
        }

        return validPassword && isPasswordMatch;
    }

    private boolean isValidPassword(String password) {
        this.pattern = Pattern.compile(PASSWORD_PATTERN);
        this.matcher = this.pattern.matcher(password);
        return this.matcher.matches();
    }

    private boolean isPasswordMatch(String password, String matchingPassword) {
        return password.equals(matchingPassword);
    }
}
