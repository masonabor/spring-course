package com.portfoliotracker.portfoliotracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/badUser")
    public String badUser() {
        return "badUser";
    }

    @GetMapping("/loginPage")
    public String loginPage() {
        return "login";
    }
}
