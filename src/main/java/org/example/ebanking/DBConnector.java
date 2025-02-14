package org.example.ebanking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    private static Connection con = null;
    private static final String CONNECT_STRING = "jdbc:mysql://localhost:3306/ChaseBank?user=root";

    private Connection openConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(CONNECT_STRING);
            con.setAutoCommit(false);
            System.out.println("Database connection established.");
            return con;
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("No connection to " + CONNECT_STRING);
            e.printStackTrace();
        }
        return null;
    }

    public static Connection getConnection() {
        try {
            if (con == null || con.isClosed()) {
                con = new DBConnector().openConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
}