package com.portfoliotracker.portfoliotracker.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

@Controller
@AllArgsConstructor
public class HomeController {

    private final LocaleResolver localeResolver;

    @GetMapping("/home")
    public String showHomePage(HttpServletRequest request) {
        return "home";
    }
}
