package com.portfoliotracker.portfoliotracker.controllers;

import com.portfoliotracker.portfoliotracker.DTO.UserLoginDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@AllArgsConstructor
public class AuthController {

    private static final String LOGIN_ERROR_SESSION_ATTR = "LOGIN_ERROR";

    @GetMapping("/badUser")
    public String badUser() {
        return "badUser";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model, HttpServletRequest request) {
        if (!model.containsAttribute("loginRequest")) {
            model.addAttribute("loginRequest", new UserLoginDTO());
        }

        HttpSession session = request.getSession(false);
        if (session != null) {
            Object error = session.getAttribute(LOGIN_ERROR_SESSION_ATTR);
            if (error != null) {
                model.addAttribute("loginError", error);
                session.removeAttribute(LOGIN_ERROR_SESSION_ATTR);
            }
        }
        return "login";
    }
}
