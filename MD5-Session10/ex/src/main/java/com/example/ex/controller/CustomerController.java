package com.example.ex.controller;

import com.example.ex.model.Customer;
import com.example.ex.model.Room;
import com.example.ex.service.CustomerIS;

import com.example.ex.service.RoomService;
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


@Controller

@RequestMapping(value = "/CustomerController")
public class CustomerController {
    @Autowired
    private CustomerIS customerService;

    private final RoomService roomService;

    @Autowired
    public CustomerController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/customersView")
    public String showCustomer(Model model) {
        List<Customer> customers = customerService.getCustomers();
        model.addAttribute("customers", customers);
        return "viewAllCustomer";
    }

    @GetMapping("/admin")
    public String showAdmin(Model model, Customer customer) {
        customer = customerService.getCustomers().get(customer.getId());
        model.addAttribute("customer", customer);
        return "adminHome";
    }

    @GetMapping("/customer")
    public String showCustomer(Model model, Customer customer) {
        customer = customerService.getCustomers().get(customer.getId());
        model.addAttribute("customer", customer);
        return "redirect:/CustomerController/viewCustomerRooms";
    }

    @GetMapping("/viewCustomerRooms")
    public String showRooms(Model model) {
        List<Room> roomList = roomService.getRoomsCustomerView();
        model.addAttribute("rooms", roomList);
        return "customerHome";
    }

    @GetMapping("/bookRoom/{id}")
    public String showBookRoom(@PathVariable("id") int id,
                               HttpSession session,
                               Model model) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            return "redirect:/AuthController/login";
        }
        model.addAttribute("customer", customer);
        model.addAttribute("id", id);
        return "bookRoom";
    }

    @PostMapping("/bookRoom/{id}")
    public String showBookRoom(@Valid @ModelAttribute("customer") Customer customer,
                               BindingResult bindingResult,
                               HttpSession session,
                               @PathVariable("id") int id,
                               Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "bookRoom";
        }
        model.addAttribute("customer", customer);
        session.setAttribute("customer", customer);
        customerService.updateCustomer(customer);
        return "redirect:/CustomerController/booked/" + id;
    }

    @GetMapping("/booked/{id}")
    public String booked(@PathVariable("id") int id) {
        roomService.bookRoom(id);
        return "redirect:/CustomerController/viewCustomerRooms";
    }
}
