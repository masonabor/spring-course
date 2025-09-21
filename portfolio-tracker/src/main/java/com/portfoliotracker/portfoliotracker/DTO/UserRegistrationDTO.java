package com.portfoliotracker.portfoliotracker.DTO;

import com.portfoliotracker.portfoliotracker.annotations.ValidEmail;
import com.portfoliotracker.portfoliotracker.annotations.ValidPassword;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@ValidPassword
public class UserRegistrationDTO {
    @NotNull(message = "{register.usernameNotNull}")
    @NotEmpty(message = "{register.usernameNotEmpty}")
    private String username;

    @NotNull(message = "{register.passwordNotNull}")
    @NotEmpty(message = "{register.passwordNotEmpty}")
    private String password;

    @NotNull(message = "{register.matchingPasswordNotNull}")
    @NotEmpty(message = "{register.matchingPasswordNotEmpty}")
    private String matchingPassword;

    @ValidEmail(message = "{register.emailNotValid}")
    @NotNull(message = "{register.emailNotNull}")
    @NotEmpty(message = "{register.emailNotEmpty}")
    private String email;
}
