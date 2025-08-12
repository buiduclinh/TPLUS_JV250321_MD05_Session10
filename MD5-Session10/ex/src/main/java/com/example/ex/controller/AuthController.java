package com.example.ex.controller;

import com.example.ex.model.Customer;
import com.example.ex.service.CustomerIS;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


import java.util.List;

@EnableWebMvc
@Controller
@Configuration
@RequestMapping(value = {"/", "/AuthController"})
public class AuthController {
    @Autowired
    private CustomerIS customerService;

    @GetMapping(value = {"/", "/login"})
    public String showLogin(Model model) {
        model.addAttribute("customer", new Customer());
        return "customerLogin";
    }

    @PostMapping(value = "/login")
    public String login(@ModelAttribute("customer") Customer customer, Model model, @RequestParam("email") String email, @RequestParam("password") String password, HttpSession session) {
        model.addAttribute("customer", customer);
        customer = customerService.login(email, password);
        if (customer != null) {
            session.setAttribute("customer", customer);
            return switch (customer.getRole()) {
                case ADMIN -> "adminHome";
                case CUSTOMER -> "customerHome";
            };
        } else {
            model.addAttribute("error", "Invalid Email or Password");
            return "customerLogin";
        }
    }

    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("customer", new Customer());
        return "customerRegister";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("customer") Customer customer,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "customerRegister";
        }

        if (customerService.phoneCheck(customer.getPhoneNumber()) != null) {
            model.addAttribute("errorPhone", "Phone is exist!");
            return "customerRegister";
        }
        if (customerService.emailCheck(customer.getEmail()) != null) {
            model.addAttribute("errorEmail", "Email is exist!");
            return "customerRegister";
        }
        customerService.addCustomer(customer);
        return "customerHome";
    }
}
