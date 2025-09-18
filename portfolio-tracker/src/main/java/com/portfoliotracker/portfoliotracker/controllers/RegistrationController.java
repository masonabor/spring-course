package com.portfoliotracker.portfoliotracker.controllers;

import com.portfoliotracker.portfoliotracker.DTO.UserRegistrationDTO;
import com.portfoliotracker.portfoliotracker.events.OnRegistrationCompleteEvent;
import com.portfoliotracker.portfoliotracker.exceptions.UserRegistrationException;
import com.portfoliotracker.portfoliotracker.models.User;
import com.portfoliotracker.portfoliotracker.models.VerificationToken;
import com.portfoliotracker.portfoliotracker.services.UserServiceInterface;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Calendar;
import java.util.Locale;


@Controller
@AllArgsConstructor
@RequestMapping("/registration")
public class RegistrationController {

    private final UserServiceInterface userService;
    private final ApplicationEventPublisher eventPublisher;
    private MessageSource messages;

    @GetMapping("/")
    public String showRegistrationForm(Model model) {
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
        model.addAttribute("user", userRegistrationDTO);
        return "registration";
    }

    @PostMapping("/register")
    public ModelAndView registerUserAccount(@ModelAttribute("user")
                                            @Valid UserRegistrationDTO userDTO,
                                            HttpServletRequest request,
                                            Errors errors) {
        var mav = new ModelAndView("registration");

        if (errors.hasErrors()) {
            return mav;
        }

        User registered;

        try {
            registered = userService.registerNewUserAccount(userDTO);
            String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), appUrl));
        } catch (UserRegistrationException e) {
            mav.addObject("globalError", e.getMessage());
            return mav;
        } catch (RuntimeException e) {
            mav.addObject("globalError", "An error occured while registration, please try later");
            return mav;
        }

        return new ModelAndView("successRegister", "user", registered);
    }

    @GetMapping("/registrationConfirm")
    public String confirmRegistration(Model model, HttpServletRequest request, @RequestParam("token") String token) {
        Locale locale = request.getLocale();

        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            String message = messages.getMessage("auth.message.invalidToken", null, locale);
            model.addAttribute("message", message);
            return "redirect:/badUser.html?lang=" + locale.getLanguage();
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            String messageValue = messages.getMessage("auth.message.expired", null, locale);
            model.addAttribute("message", messageValue);
            return "redirect:/badUser.html?lang=" + locale.getLanguage();
        }

        user.setActivated(true);
        userService.saveRegisteredUser(user);
        return "redirect:/login.html?lang=" + locale.getLanguage();
    }
}
