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
@RequestMapping(value = "/CustomerController")
public class CustomerController {
    @Autowired
    private CustomerIS customerService;

    @GetMapping("/customersView")
    public String showCustomer(Model model) {
        List<Customer> customers = customerService.getCustomers();
        model.addAttribute("customers", customers);
        return "viewAllCustomer";
    }

    @GetMapping("/admin")
    public String showAdmin(Model model, Customer customer) {
        model.addAttribute("customers", customerService.getCustomers());
        return "adminHome";
    }

    @GetMapping("/customer")
    public String showCustomer(Model model, Customer customer) {
        model.addAttribute("customers", customerService.getCustomers());
        return "customerHome";
    }
}
