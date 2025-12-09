package com.lab1.helloworldweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/lab3")
public class MainController {

    @GetMapping("/portfolio")
    public String showPortfolio(Model model) {
        // 2.1 Умовне форматування - дані для Elvis operator та if/unless
        model.addAttribute("<script>alert('XSS')</script>", "Octopus663");
        model.addAttribute("portfolioValue", 15234.75);
        model.addAttribute("hasData", true);

        // 2.2 Елемент вибору - різні статуси для switch/case
        model.addAttribute("accountStatus", "pending"); // змінюйте на: active, inactive, pending

        // 2.3 Цикл - список акцій для forEach
        List<Stock> stocks = Arrays.asList(
                new Stock("AAPL", "Apple Inc.", 150.25, 10, "technology"),
                new Stock("GOOGL", "Alphabet Inc.", 2800.00, 5, "technology"),
                new Stock("MSFT", "Microsoft Corp.", 300.50, 8, "technology"),
                new Stock("TSLA", "Tesla Inc.", 750.00, 3, "automotive"),
                new Stock("AMZN", "Amazon.com Inc.", 3300.00, 2, "retail")
        );
        model.addAttribute("stocks", stocks);

        return "Portfolio";
    }

    // Внутрішній клас для представлення акцій
    public static class Stock {
        private String symbol;
        private String name;
        private double price;
        private int quantity;
        private String sector;

        public Stock(String symbol, String name, double price, int quantity, String sector) {
            this.symbol = symbol;
            this.name = name;
            this.price = price;
            this.quantity = quantity;
            this.sector = sector;
        }

        public String getSymbol() { return symbol; }
        public String getName() { return name; }
        public double getPrice() { return price; }
        public int getQuantity() { return quantity; }
        public String getSector() { return sector; }
        public double getTotalValue() { return price * quantity; }
    }
}