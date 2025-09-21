package com.portfoliotracker.portfoliotracker.controllers;

import com.portfoliotracker.portfoliotracker.DTO.UserLoginDTO;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
public class AuthController {

    @GetMapping("/badUser")
    public String badUser() {
        return "badUser";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        model.addAttribute("loginRequest",  userLoginDTO);

        Object error = session.getAttribute("error");
        if (error != null) {
            mav.addObject("loginError", error);
            session.removeAttribute("loginError");
        }
        return "login";
    }
}
