package com.portfoliotracker.portfoliotracker.config;

import com.portfoliotracker.portfoliotracker.services.MyUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.util.Locale;

@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity
@AllArgsConstructor
public class WebSecurityConfig {

    private final MyUserDetailsService userDetailsService;
    private final MessageSource messageSource;

    private static final String LOGIN_ERROR_SESSION_ATTR = "LOGIN_ERROR";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/login", "/registration/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login") // обробник аутенфікації
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/home", true)
                        .failureHandler(authenticationFailureHandler())
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .permitAll()
                )
                .httpBasic(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        return builder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            Locale locale = LocaleContextHolder.getLocale();
            System.out.println("from handler " +locale.getLanguage());
            String messageKey = switch (exception) {
                case BadCredentialsException _ ->
                        "AbstractUserDetailsAuthenticationProvider.badCredentials";
                case DisabledException _ -> "AbstractUserDetailsAuthenticationProvider.disabled";
                case LockedException _ -> "AbstractUserDetailsAuthenticationProvider.locked";
                case UsernameNotFoundException _ -> "auth.userNotFound";
                case null, default -> "auth.message.default";
            };

            String errorMessage = messageSource.getMessage(messageKey, null, "Invalid credentials", locale);

            request.getSession().setAttribute(LOGIN_ERROR_SESSION_ATTR, errorMessage);

            response.sendRedirect(request.getContextPath() + "/login?error");
        };
    }
}
