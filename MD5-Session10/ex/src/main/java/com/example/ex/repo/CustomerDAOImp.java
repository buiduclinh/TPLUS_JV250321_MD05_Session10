package com.example.ex.repo;

import com.example.ex.model.Customer;
import com.example.ex.ulti.DBConnection;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomerDAOImp implements CustomerDAO {
    @Override
    public List<Customer> getCustomers() {
        String sql = "{CALL view_customers()}";
        List<Customer> customers = new ArrayList<>();
        try (Connection connection = DBConnection.getInstance().getConnection();
             CallableStatement stmt = connection.prepareCall(sql);) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setFullName(rs.getString("full_name"));
                customer.setEmail(rs.getString("email"));
                customer.setPassword(rs.getString("password"));
                customer.setAddress(rs.getString("address"));
                customer.setPhoneNumber(rs.getString("phone_number"));
                customer.setRole(Customer.Role.valueOf(rs.getString("role")));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public Customer emailCheck(String email) {
        String sql = "{CALL get_email(?)}";
        try (Connection connection = DBConnection.getInstance().getConnection();
             CallableStatement stmt = connection.prepareCall(sql);) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Customer customer = new Customer();
                customer.setEmail(rs.getString("email"));
                return customer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Customer phoneCheck(String phoneNumber) {
        String sql = "{CALL get_phone(?)}";
        try (Connection connection = DBConnection.getInstance().getConnection();
             CallableStatement stmt = connection.prepareCall(sql);) {
            stmt.setString(1, phoneNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Customer customer = new Customer();
                customer.setPhoneNumber(rs.getString("phone_number"));
                return customer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Customer login(String email, String password) {
        String sql = "{CALL login(?,?)}";
        try (Connection connection = DBConnection.getInstance().getConnection();
             CallableStatement callableStatement = connection.prepareCall(sql);) {
            callableStatement.setString(1, email);
            callableStatement.setString(2, password);
            ResultSet rs = callableStatement.executeQuery();
            if (rs.next()) {
                Customer customer = new Customer();
                customer.setEmail(rs.getString("email"));
                customer.setPassword(rs.getString("password"));
                customer.setRole(Customer.Role.valueOf(rs.getString("role")));
                customer.setFullName(rs.getString("full_name"));
                customer.setId(rs.getInt("id"));
                customer.setPhoneNumber(rs.getString("phone_number"));
                customer.setAddress(rs.getString("address"));
                return customer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addCustomer(Customer customer) {
        String sql = "{CALL register(?,?,?,?,?)}";
        try (Connection connection = DBConnection.getInstance().getConnection();
             CallableStatement callableStatement = connection.prepareCall(sql);) {
            callableStatement.setString(1, customer.getFullName());
            callableStatement.setString(2, customer.getPhoneNumber());
            callableStatement.setString(3, customer.getEmail());
            callableStatement.setString(4, customer.getPassword());
            callableStatement.setString(5, customer.getAddress());
            int row = callableStatement.executeUpdate();
            if (row > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        String sql = "{CALL update_customer(?,?,?,?)}";
        try (Connection connection = DBConnection.getInstance().getConnection();
             CallableStatement callableStatement = connection.prepareCall(sql);) {
            callableStatement.setInt(1, customer.getId());
            callableStatement.setString(2, customer.getFullName());
            callableStatement.setString(3, customer.getPhoneNumber());
            callableStatement.setString(4, customer.getAddress());
            int row = callableStatement.executeUpdate();
            if (row > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
