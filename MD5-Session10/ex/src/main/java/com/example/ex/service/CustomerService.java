package com.example.ex.service;

import com.example.ex.model.Customer;
import com.example.ex.repo.CustomerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements CustomerIS {
    @Autowired
    private CustomerDAO customerDAO;

    @Override
    public List<Customer> getCustomers() {
        return customerDAO.getCustomers();
    }

    @Override
    public Customer emailCheck(String email) {
        return customerDAO.emailCheck(email);
    }

    @Override
    public Customer phoneCheck(String phoneNumber) {
        return customerDAO.phoneCheck(phoneNumber);
    }

    @Override
    public Customer login(String email, String password) {
        return customerDAO.login(email, password);
    }

    @Override
    public boolean addCustomer(Customer customer) {
        return customerDAO.addCustomer(customer);
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        return customerDAO.updateCustomer(customer);
    }
}
