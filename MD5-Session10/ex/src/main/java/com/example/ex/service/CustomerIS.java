package com.example.ex.service;

import com.example.ex.model.Customer;

import java.util.List;

public interface CustomerIS {
    List<Customer> getCustomers();

    Customer emailCheck(String email);

    Customer phoneCheck(String phoneNumber);

    Customer login(String email, String password);

    boolean addCustomer(Customer customer);
}
