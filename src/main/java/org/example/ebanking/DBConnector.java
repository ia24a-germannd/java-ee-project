package org.example.ebanking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private static final String CONNECT_STRING = "jdbc:mysql://localhost:3306/ChaseBank?user=root";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load JDBC Driver when getConnection() is called
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("❌ MySQL JDBC Driver not found!", e);
        }

        return DriverManager.getConnection(CONNECT_STRING);
    }
}