package com.portfoliotracker.portfoliotracker.controllers;

import com.portfoliotracker.portfoliotracker.DTO.UserLoginDTO;
import com.portfoliotracker.portfoliotracker.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/badUser")
    public String badUser() {
        return "badUser";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        model.addAttribute("loginRequest",  userLoginDTO);
        return "login";
    }
}
