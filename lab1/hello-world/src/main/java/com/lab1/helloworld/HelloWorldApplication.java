package com.lab1.helloworld;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloWorldApplication implements CommandLineRunner {

    public static void main(String[] args) {
        System.out.println("start");
        SpringApplication.run(HelloWorldApplication.class, args);
        System.out.println("finish");
    }

    @Override
    public void run(String... args) {
        System.out.println("Hello World!");
    }
}
