package com.portfoliotracker.portfoliotracker.DTO;

import com.portfoliotracker.portfoliotracker.annotations.ValidEmail;
import com.portfoliotracker.portfoliotracker.annotations.ValidPassword;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@ValidPassword
public class UserRegistrationDTO {
    @NotNull
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
    private String password;

    private String matchingPassword;

    @ValidEmail
    @NotNull
    @NotEmpty
    private String email;
}
