package com.example.ex.ulti;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection instance;
    private static final String URL = "jdbc:mysql://localhost:3306/hotel_database_1";
    private static final String USER = "root";
    private static final String PASS = "123456789";

    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL,USER,PASS);
    }

    public DBConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }
}
