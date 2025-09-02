package com.lab1.helloworldweb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.util.Objects;

@Controller
public class StudentController {

    @GetMapping("/")
    public String mainPage() {
        return "mainPage";
    }

    @GetMapping("/view/{student}")
    public ModelAndView viewPage(@PathVariable() String student) {
        var modelAndView = new ModelAndView("viewPage");
        if (student.equals("andriy")) {
            modelAndView.addObject("student", readFile("andriy.txt"));
            return modelAndView;
        } else if (student.equals("tania")) {
            modelAndView.addObject("student", readFile("tania.txt"));
            return modelAndView;
        } else {
            modelAndView.addObject("student", readFile("yurii.txt"));
            return modelAndView;
        }
    }

    String readFile(String fileName) {
        var text = new StringBuilder();
        try (var br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass()
                        .getClassLoader()
                        .getResourceAsStream(fileName)))) ) {
            String line = br.readLine();
            do {
                text.append(line);
                line = br.readLine();
            } while (line != null);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return text.toString();
    }
}
