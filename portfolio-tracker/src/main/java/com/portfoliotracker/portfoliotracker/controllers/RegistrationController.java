package com.portfoliotracker.portfoliotracker.controllers;

import com.portfoliotracker.portfoliotracker.DTO.UserRegistrationDTO;
import com.portfoliotracker.portfoliotracker.exceptions.UserRegistrationException;
import com.portfoliotracker.portfoliotracker.models.User;
import com.portfoliotracker.portfoliotracker.services.UserServiceInterface;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;

@Controller
@AllArgsConstructor
@RequestMapping("/registration")
public class RegistrationController {

    private final UserServiceInterface userService;
    private final SpringResourceTemplateResolver springResourceTemplateResolver;

    @GetMapping("/")
    public String showRegistrationForm(Model model) {
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
        model.addAttribute("user", userRegistrationDTO);
        return "registration";
    }

    @PostMapping("/register")
    public ModelAndView registerUserAccount(@ModelAttribute("user")
                                            @Valid UserRegistrationDTO userDTO,
                                            Errors errors) {
        var mav = new ModelAndView("registration");

        if (errors.hasErrors()) {
            return mav;
        }

        User registered;

        try {
            registered = userService.registerNewUserAccount(userDTO);
        } catch (UserRegistrationException e) {
            mav.addObject("globalError", e.getMessage());
            return mav;
        }

        return new ModelAndView("successRegister", "user", registered);
    }
}
